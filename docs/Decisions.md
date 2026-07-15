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