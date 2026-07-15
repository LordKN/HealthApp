# Architectural Decisions

## I. Why React + Spring Boot?

GrindHub uses React for the frontend and Spring Boot for the backend.

React was chosen because it provides:

- Reusable UI components (navbar, footer)
- Client-side routing (avoid asking backend for every page)
- Efficient state management (Avoid manipulating the DOM constantly)
- Clean user experience

Java and Spring Boot was chosen because it provides:

- RESTful API development
- Dependency injection
- Layered architecture
- Validation
- JPA/Hibernate integration
- Spring Security support
- Testing Tools
- Enterprise-level structure and scalability
- Kotlin was considered as an alternative JVM language, but Java was preferred so the project could focus on backend architecture and framework concepts without introducing a second language-learning curve.
- Spring Boot was selected because its structure, strong Java ecosystem, and enterprise usage aligned best with the learning goals of the project. In particular, Spring Boot has strong conventions between features that allow them to work together. From this, I can focus on solving the problem rather than configuring each feature/library separately.

## II. Why MySQL instead of NoSQL?

GrindHub stores highly structured and interconnected data. Entities such as clients, coaches, certificates, medical histories, workout plans, and nutrition plans have well-defined relationships and consistent schemas.

For this reason, MySQL is a better fit than a document-oriented NoSQL database.

### Example

Suppose Coach John has three professional certifications.

```text
Coach
-------------
| id | name |
-------------
| 1  | John |
-------------

Certificate
---------------------------------
| id | name          | coach_id |
---------------------------------
| 1  | NASM CPT      |    1     |
| 2  | CPR           |    1     |
| 3  | First Aid     |    1     |
--------------------------------
```

The "coach_id" foreign key guarantees that every certificate belongs to a valid coach. If a coach deleted, the certificate(s) associated with that coach will be deleted accordingly or the coach will not be allowed to delete based on the relationship configuration.

In a document-oriented NoSQL database, this relationship is typically managed by the application. The database does not automatically enforce that a reference coach exists, so the developer must be responsible for handling the relationship manually.

→ Therefore, a relational database provides stronger data integrity and simplifies querying related information.

