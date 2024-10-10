# Informe de Implementación del Modelo de Control de Acceso por el Origen en un Sistema de Login

## 1. Introducción

En este informe se documenta la implementación del Modelo de Control de Acceso por el Origen (OBAC) en un sistema de autenticación basado en un servidor web desarrollado en **ASP.NET Core**. El objetivo es restringir el acceso al sistema de login según la dirección IP de origen del cliente, asegurando que solo los usuarios desde ciertas IPs puedan autenticarse en la aplicación.

El control de acceso basado en el origen proporciona una capa adicional de seguridad, especialmente útil en entornos corporativos o de alto nivel donde las conexiones desde ubicaciones no autorizadas deben ser restringidas.

## 2. Objetivos

- Implementar el Modelo de Control de Acceso por el Origen en el sistema de autenticación de la aplicación.
- Restringir el acceso al sistema de login a un conjunto predefinido de direcciones IP.
- Garantizar que los usuarios no autorizados sean bloqueados antes de intentar iniciar sesión, reduciendo la superficie de ataque.

## 3. Tecnologías Utilizadas

- **Lenguaje de programación**: C#
- **Framework**: ASP.NET Core
- **Entorno de desarrollo**: Visual Studio / Visual Studio Code
- **Servidor web**: Kestrel (o IIS, según configuración)
- **Sistema operativo**: Windows / Linux
- **Navegador web**: Cualquier navegador compatible con solicitudes HTTP

## 4. Diseño e Implementación

### 4.1 Estructura del Proyecto

El proyecto está organizado de la siguiente manera:
- **Controladores**: Contienen la lógica para gestionar las peticiones HTTP, incluido el sistema de login.
- **Middleware**: Incluye la lógica de filtrado por IP para aplicar el Modelo OBAC antes de procesar cualquier solicitud.
- **Servicios de Autenticación**: Gestionan la verificación de credenciales y la creación de tokens de sesión.

### 4.2 Middleware de Control de Acceso por IP

El corazón de la implementación del Modelo OBAC está en el middleware que intercepta las solicitudes antes de llegar al sistema de login.

#### Código del Middleware

```csharp
app.Use(async (context, next) =>
{
    // Obtener la dirección IP del cliente
    var ipAddress = context.Connection.RemoteIpAddress?.ToString();

    // Lista de IPs permitidas
    var allowedIPs = new List<string>
    {
        "192.168.1.100", // IP de ejemplo permitida
        "203.0.113.5"    // Otra IP permitida
    };

    // Verificar si la IP está permitida
    if (allowedIPs.Contains(ipAddress))
    {
        // Si la IP está permitida, continuar con la solicitud
        await next();
    }
    else
    {
        // Si la IP no está permitida, devolver un código 403 (Prohibido)
        context.Response.StatusCode = StatusCodes.Status403Forbidden;
        await context.Response.WriteAsync("Acceso denegado: su IP no está permitida.");
    }
});
```
