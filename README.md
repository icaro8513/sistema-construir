# Sistema Construir

## Descripción del sistema

Construx-Com es un sistema web de bolsa de trabajo orientado al rubro de la construcción. Permite la interacción entre clientes que necesitan publicar trabajos y profesionales que ofrecen sus servicios.

## Objetivo

El objetivo del sistema es facilitar el contacto entre clientes y profesionales de la construcción, permitiendo gestionar usuarios, trabajos, cotizaciones, calificaciones y comunicación entre las partes.

## Tecnologías utilizadas

- Java
- Spring Boot
- Gradle
- HTML
- CSS
- MySQL
- phpMyAdmin
- IntelliJ IDEA
- GitHub

## Funcionalidades principales

- Registro e inicio de sesión de usuarios.
- Gestión de clientes.
- Gestión de profesionales.
- Publicación de trabajos.
- Cotizaciones.
- Calificaciones entre usuarios.
- Mensajería o chat entre cliente y profesional.
- Administración de datos mediante base de datos relacional.

## Base de datos

La base de datos del sistema se encuentra exportada en formato SQL dentro de la carpeta:

`database/sistema_construir.sql`

Este archivo contiene la estructura de las tablas y los datos necesarios para reconstruir la base de datos del sistema.

## Tipo de base de datos

El sistema utiliza una base de datos relacional en MySQL. Las tablas se relacionan mediante claves primarias y claves foráneas, permitiendo mantener la integridad de los datos entre clientes, profesionales, trabajos, cotizaciones, calificaciones y mensajes.

## Cómo ejecutar el proyecto

1. Clonar el repositorio desde GitHub.
2. Abrir el proyecto en IntelliJ IDEA.
3. Importar la base de datos desde phpMyAdmin usando el archivo SQL ubicado en la carpeta `database`.
4. Configurar los datos de conexión en `application.properties`.
5. Ejecutar la aplicación desde IntelliJ IDEA.

## Escalabilidad del sistema

El sistema fue pensado para poder crecer en futuras versiones. Algunas posibles mejoras de escalabilidad funcional son:

- Permitir que los profesionales también publiquen trabajos como contratistas.
- Agregar un sistema de pagos dentro de la plataforma.
- Incorporar filtros avanzados de búsqueda por zona, especialidad, reputación o disponibilidad.
- Implementar notificaciones automáticas para clientes y profesionales.
- Agregar un panel de administración para controlar usuarios, trabajos y reportes.
- Mejorar la seguridad del sistema mediante roles y permisos más específicos.
- Preparar el sistema para soportar mayor cantidad de usuarios y publicaciones.

## Estado del proyecto

El sistema se encuentra en desarrollo y fue realizado como proyecto académico para la materia Programación.