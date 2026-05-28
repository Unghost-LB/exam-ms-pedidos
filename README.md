# Microservicio: ms-pedidos

## Descripción
Microservicio encargado de la gestión de pedidos dentro del ecosistema de CodiGo. Permite el registro de operaciones, cálculo automático de totales en el backend y la gestión de estados (REGISTRADO, PAGADO, CANCELADO, DEVUELTO) garantizando la integridad de los datos financieros.

## Tecnologías Utilizadas
* **Java 21** / Spring Boot 3.x
* **Gestor de dependencias**: Maven
* **Base de Datos**: PostgreSQL (Neon.tech)
* **Contenedorización**: Docker
* **Despliegue**: Render

## Endpoints Disponibles
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/pedidos` | Crea un nuevo pedido con cálculo automático de total. |
| `GET` | `/api/pedidos` | Lista todos los pedidos registrados. |
| `GET` | `/api/pedidos/{id}` | Busca un pedido específico. |
| `PATCH` | `/api/pedidos/{id}/estado` | Actualiza el estado del pedido con validación de lógica de negocio. |

## Variables de Entorno Necesarias
* `SPRING_DATASOURCE_URL`
* `SPRING_DATASOURCE_USERNAME`
* `SPRING_DATASOURCE_PASSWORD`

## Instrucciones para Ejecutar en Local
1. Clonar el repositorio.
2. Configurar la conexión a la base de datos de Neon en `src/main/resources/application.properties`.
3. Ejecutar: `mvn spring-boot:run`

## Instrucciones Básicas de Despliegue
Este proyecto está configurado para despliegue automático en **Render**:
1. Conectar el repositorio de GitHub a Render.
2. Configurar el *Build Command*: `mvn clean package -DskipTests`
3. Configurar el *Start Command*: `java -jar target/*.jar`
4. Añadir las variables de entorno de conexión a base de datos.

En este caso las conexiones a Render ya se realizaron.
Configuración de la petición:
1. Abre Postman y crea una nueva Request.
2. Selecciona el método HTTP correspondiente (ej. GET para listar, POST para crear).
3. En la barra de direcciones, escribe el siguiente endpoint:
Enlace de despliegue en Render (API lista para probar en Postman):
 - Pedidos: https://ms-pedidos-yt91.onrender.com/api/pedidos
---
**Examen Final | Docente: Nike Rodriguez**
**CodiGo By Tecsup**
