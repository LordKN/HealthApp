# GrindHub Authentication and Authorization

## 1. Overview

GrindHub uses JWT-based authentication with:
 - Access Token
 - Refresh Token
 - Refresh Session
 - Role - Based Authorization

The system supports three roles: 
 - Client
 - Coach
 - Admin

### Main Idea

Authentication Answers: "Who are you?"

Authorization Answers: "What are you allowed to do"

In GrindHub:
 - JWT Filter = Authentication
 - SecurityConfig = Authorization

---
# 2. Token Architecture

GrindHub uses two JWTs.

## Access Token

Purpose:
 - Access protected API endpoints
 - Sent frequently with API requests
 - Short-lived
 - Stored in React memory
 - Not stored in database

Sent through:
Authorization: Bearer <access-token>

Current important claim: sub = user's email

## Refresh token

Purpose: 
 - Generate a new access token
 - Keep the user logged in without asking for password again
 - Longer-lived
 - Stored as an HttpOnly cookie

Contains:
 - sessionId
 - tokenId

The raw refreshToken is NOT stored in MySQL.

## Refresh Session

RefreshSession is stored in MySQL.

It allows the backend to control whether a refresh token should still be accepted.

Important fields:

 - id
 - userId
 - role
 - sessionId
 - currentTokenId
 - createdAt
 - expiresAt
 - revoked
 - revokedAt
 - revocationReason

### ID Mental model

 - userId = Who owns the session
 - sessionId = which login session
 - tokenId = WHICH refresh-token version

---

# 3. Login FLow

Endpoint: 

POST /api/auth/login

## Step 1: React sends Credentials

Example:  
{  
&emsp; "username": "user@example.com"  
&emsp; "password": password  
}  

Credential DTO:
public record Credential {  
&emsp; String username,  
&emsp; String password  
) {}  

Although the field is called username, GrindHub currently uses the user's email. 

## Step 2: AuthController Creates Authentication Request

AuthController creates:

new UsernamePasswordAuthenticationToken (  
&emsp; credential.username,  
&emsp; credential.password  
)

Then sends it to:

AuthenticationManager

## Step 3: Spring Authenticates the User

Flow:  
AuthenticationManager  
↓  
DaoAuthenticationProvider  
↓  
MyUserDetailService  
↓  
Find user from database  
↓  
UserPrincipal  
↓  
PasswordEncoder  
↓  
Compare password with BCrypt hash  

MyUserDetailService checks:  

 - ClientRepository
 - CoachRepository
 - AdminRepository

## Step 4: Authentication Succeeds

Spring returns an Authentication object.

From the UerPrincipal, GrindHub can obtain:
 - User ID
 - Email
 - Role
 - Authorities 

The password does not need to be used again after authentication

## Step 5: Generate Access Token

JwtService generates an access JWT.

Important value:

sub = user's email

React receives this token and stores it in memory

## Step 6: Create RefreshToken

A new RefreshSession is created:

 - userId
 - role 
 - sessionId
 - currentTokenId
 - createdAt
 - expiresAt
 - revoked = false

## Step 7: Generate Refresh Token

JwtService creates a refresh JWT containing:

sessionId
tokenId

## Step 8: Store Refresh Token in Cookie

The backend sends:

refreshToken = "JWT"

with  

HttpOnly = true    
Path = /api/auth  
SameSite = Lax  
Max-Age = 7 days  

- Development: Secure = false
- Production: Secure = true

## Complete Login Flow

React  
↓  
username + password  
↓  
AuthController  
↓  
AuthenticationManager  
↓  
DaoAuthenticationProvider  
↓  
MyUserDetailService  
↓  
UserPrincipal  
↓  
BCrypt password verification  
↓  
Authentication succeeds  
│  
├── Generate Access JWT  
│       ↓  
│   React Memory  
│  
└── Create RefreshSession  
↓  
Generate Refresh JWT  
↓  
HttpOnly Cookie  

# 4. Protected Request Flow

Example:  
POST /api/admin/import/categories

Authorization: Bearer <access-token>

## Step 1: Request Enters Security Filter Chain

The JwtFilter runs before the request reaches the controller

## Step 2: JwtFilter Checks Authorization Header

JwtFilter checks:  
Authorization: Bearer ...

If there is no Authorization header:

JwtFilter continues the filter chain.

It does NOT automatically return 401 because some endpoints, such as login, are public.

## Step 3: Validate Access JWT

JwtService:
 - Verifies JWT signature
 - Verifies expiration
 - Extracts user's email

## Step 4: Load Current User

The email is passed to:

**MyUserDetailService** which loads the user from MySQL and returns **UserPrincipal**

## Step 5: Create Authentication

JwtFilter creates:  

new UsernamePasswordAuthenticationToken(   
&emsp; userDetails,  
&emsp; null,  
&emsp; userDetails.getAuthorities()  
)

## Step 6: Store Authentication

The Authentication object is placed into:  

**SecurityContextHolder**

This tells Spring Security: "This request belongs to this authenticated user."

## Step 7: Authorization

SecurityConfig checks whether the authenticated user is allowed to access this endpoint.

Example:

.requestMatchers("/api/admin/**")  
.hasAuthority("ADMIN")

**ADMIN:**

Valid JWT  
↓  
JwtFilter authenticates user  
↓  
SecurityConfig sees ADMIN  
↓  
Controller executes  


**CLIENT:**

Valid JWT  
↓  
JwtFilter authenticates user  
↓  
SecurityConfig sees CLIENT  
↓  
ADMIN required  
↓  
403 Forbidden  


## Complete Protected Request Flow

React  
↓  
Authorization: Bearer <Access JWT>  
↓  
JwtFilter  
↓  
JwtService validates JWT  
↓  
Extract email  
↓  
MyUserDetailService  
↓  
UserPrincipal  
↓  
Create Authentication  
↓  
SecurityContextHolder  
↓  
SecurityConfig  
↓  
Check Authority  
↓  
Controller  
---

# 5. Refresh Flow

Endpoint:

POST /api/auth/refresh

## Why Refresh Is Needed

The access token should be short-lived

When it expires, the user should not have to enter their password again if there refresh session is still valid  

## Step 1: Access Token Expires

Protected API:

React  
↓  
Expired access JWT  
↓  
401 Unauthorized  

## Step 2: React Requests Refresh

React calls:

POST /api/auth/refresh

The browser automatically sends the HttpOnly refresh cookie. 

React JavaScript does NOT read the refresh token directly.

## Step 3: Validate Refresh JWT

JwtService verifies: 
- Signature
- JWT expiration

Then extracts:

 - sessionId
 - tokenId

## Step 4: Find Refresh Session
RefreshSessionService finds the database row using **sessionId**

## Step 5: Validate Server-Side Session

The request is rejected if:  
session.revoked == true  
OR  
session.expiresAt < current time  
OR  
session.currentTokenId != tokenId

## Step 6: Rotate Refresh Token

If valid:  

**Old:**  
sessionId = AAA  
tokenId = 111

**After refresh:**  
sessionId = AAA  
tokenId = 222

**_Important:_**
 - session id stays the same
 - tokenId changes.

## Step 7: Generate New Tokens

Backend generates:  

New Access JWT  
↓  
React Memory  

New Refresh JWT  
↓  
HttpOnly Cookie  

## Step 8: Retry Original Request

React can retry the API request using the new access JWT. 

## Complete Refresh Flow

Access JWT expires  
↓  
API returns 401  
↓  
POST /api/auth/refresh  
↓  
Browser sends HttpOnly cookie  
↓  
Validate Refresh JWT  
↓  
Extract sessionId + tokenId  
↓  
Find RefreshSession  
↓  
Check:  
- revoked?  
- expired?  
- correct tokenId?  
  ↓  
  Rotate tokenId  
  ↓  
  Generate new Access JWT  
  ↓  
  Generate new Refresh JWT  
  ↓  
  Access JWT → React  
  Refresh JWT → Cookie  
  ↓  
  Retry original request  
---

# 6. Refresh Token Rotation
Refresh-token rotation prevents an old refresh token from remaining valid forever

Example:

**Database:**

sessionId = AAA  
currentTokenId = 111

**Refresh JWT:**

sessionId = AAA
tokenId = 111

They match: 111 == 111

**VALID**

After successful refresh:

currentTokenId = 222

New Refresh JWT:

tokenId = 222

If someone tries the old token, it will be a mismatch, and 401 will be thrown

# 7. Page Reload
The access JWT is stored only in React memory. When the user presses F5, React reloads, and the access JWT
will disappear. However, the browser still has the HttpOnly refresh cookie.

Therefore:
Page reload / F5  
↓  
Access JWT disappears from React memory  
↓  
React calls POST /api/auth/refresh  
↓  
Browser sends existing HttpOnly refresh token  
↓  
Backend validates refresh JWT  
↓  
Find RefreshSession  
↓  
Validate sessionId + tokenId  
↓  
ROTATE tokenId  
111 → 222  
↓  
Generate NEW access JWT  
↓  
Generate NEW refresh JWT using tokenId 222  
↓  
Replace old HttpOnly refresh cookie  
↓  
User stays logged in  

# 8. Logout Flow

Endpoint:

POST /api/auth/logout

Logout performs three actions:
1. Revoke the RefreshSession
2. Delete the refresh cookie
3. Delete the access JWT from React memory.

## Backend

Refresh cookie  
↓  
Extract sessionId + tokenId  
↓  
Find RefreshSession  
↓  
Set:  

revoked = true  
revokedAt = now  
revocationReason = LOGOUT  

### Clear Cookie
Backend sends:  
refreshToken = "", Max-Age = 0;

## Frontend
React deletes its in-memory access token

## Complete Logout Flow
React  
↓  
POST /api/auth/logout  
↓  
Refresh Cookie  
↓  
Backend finds RefreshSession  
↓  
revoked = true  
↓  
Delete Refresh Cookie  
↓  
React deletes Access JWT  
↓  
Logged Out  

# 9. Multiple Device Sessions
Every login can create a separate RefreshSession
Example:

User  
│  
├── Chrome: sessionId = AAA  
│  
└── Phone:  sessionId = BBB  

When logging out on Chrome, the RefreshSession with id = AAA becomes invalid, while the 
RefreshSession with id = BBB still valid.

In the future, "Log Out All Devices" feature could revoke every active RefreshSession belonging to the user.

---

# 10. Security Responsibilities

## AuthenticationManager
Authenticates username + password during login.

## DaoAuthenticationProvider
Connects Spring Security authentication with:
- UserDetailsService
- PasswordEncoder

## MyUserDetailService
Finds the HealthApp user from the database.

## UserPrincipal
 - Converts HealthApp's Person into a format Spring Security understands
 - Provides:
   - Email
   - Password
   - ID
   - Role
   - Authorities

## JwtService
Responsible for:
- Generating access JWTs
- Generating refresh JWTs
- Signing JWTs
- Parsing JWTs
- Extracting claims

## JwtFilter
Authenticates normal API requests using the access JWT.

## SecurityContextHolder
Stores the Authentication object for the current request.

## SecurityConfig
Defines authorization rules.

Example:

/api/auth/**  → public

/api/admin/** → ADMIN only

other APIs    → authenticated user

## RefreshSessionService
- Controls long-lived login sessions
- Responsible for:
  - Creating refresh sessions
  - Validation refresh sessions
  - Rotating tokenId
  - Extending expiration
  - Revoking sessions

---

# 12. Token Storage

| Data | Location |
|---|---|
| Access JWT | React memory |
| Refresh JWT | HttpOnly browser cookie |
| RefreshSession | MySQL |
| BCrypt password hash | MySQL |
| Raw password | Never stored |

# 13. Current Development Settings
**These settings are acceptable for local development but should be revisited before production**

## Access Token Expiration

Current development access-token lifetime is longer.  
Target later: 10-15 minutes

## Cookie Security

- Development: secure(false)
- Production: secure(true)

## CSRF
CSRF is currently disabled. Because refresh tokens are stored in cookies, CSRF protection should be viewed before production

## CORS
React and Spring Boot run on different origins during development.

Credentialed requests need:
credentials: "include"

The backend should explicitly allow the React origin and credentials

---

# 14. Frontend Authentication & Token Handling

## a) Flow
Frontend Authentication & Token Handling
│  
├── AuthContext / AuthProvider  
│&emsp;&emsp;   ├── accessToken stored in React memory  
│&emsp;&emsp;   ├── role stored in React memory  
│&emsp;&emsp;   └── loading state during session restoration  
│  
├── Login  
│&emsp;&emsp;   ├── POST /api/auth/login  
│&emsp;&emsp;   ├── access token → React AuthContext  
│&emsp;&emsp;   └── refresh token → HttpOnly cookie  
│  
├── Protected Requests  
│&emsp;&emsp;   └── Authorization: Bearer <accessToken>  
│  
├── Access Token Expiration  
│&emsp;&emsp;   ├── protected request → 401  
│&emsp;&emsp;   ├── POST /api/auth/refresh  
│&emsp;&emsp;   ├── receive new access token  
│&emsp;&emsp;   ├── update AuthContext  
│&emsp;&emsp;   └── retry original request once  
│  
├── Page Reload / F5  
│&emsp;&emsp;   ├── React memory disappears  
│&emsp;&emsp;   ├── HttpOnly refresh cookie survives  
│&emsp;&emsp;   ├── AuthProvider calls /auth/refresh  
│&emsp;&emsp;   └── authentication state restored  
│  
├── Failed Refresh  
│&emsp;&emsp;   └── clear authentication state / require login  
│  
└── Current Edge Cases  
&emsp;&emsp;├── simultaneous refresh requests  
&emsp;&emsp;└── refresh-token rotation race  

## b) Definition

### AuthContext
- A React Context that creates a shared location through which authentication information can be accessed
by components throughout the application.
- Prevents authentication data from having to be manually passed through multiple components as props
- Allows components to access authentication information provided by AuthProvider
- **Does not own the authentication state**

### AuthProvider
- A React component that owns, manages, and provides the application's authentication state through AuthContext.
- Wraps the application so the child components can access the authentication information/
- Responsible for:
  - Storing authentication state: role, accessToken
  - Providing authentication information to child components
  - Restoring authentication after a page reload