# 🧩 PriceScout Config Server

Este directorio (rama `config`) contiene todos los archivos de configuración centralizados utilizados por los microservicios de **PriceScout API**.  
El propósito es demostrar el uso de **Spring Cloud Config Server** conectado a un repositorio Git como fuente de configuración, simulando un entorno real de microservicios distribuido.

---

## 🎯 Objetivo

El **Config Server** permite que todos los servicios de PriceScout obtengan sus propiedades externas (puertos, bases de datos, endpoints, etc.) sin necesidad de incluirlas en su código.  
Esto facilita:
- **Gestión centralizada de entornos** (`dev`, `prod`, etc.).
- **Cambios dinámicos** sin recompilar ni desplegar de nuevo.
- **Escalabilidad y mantenibilidad** en arquitecturas distribuidas.

---

## ⚙️ Configuración del Servidor

En el proyecto principal, el servicio `config-server` está configurado para leer desde esta rama del mismo repositorio.

**Archivo:** `config-server/src/main/resources/application.yml`

```yaml
server:
  port: 8888

spring:
  application:
    name: config-server
  cloud:
    config:
      server:
        git:
          uri: https://github.com/Mateo-404/PriceScout-Microservices-API.git
          default-label: config       # Rama donde se encuentra esta configuración
          clone-on-start: true
          search-paths: config        # Carpeta raíz de configuración
````

---

## 📁 Estructura de Archivos

```
config/dev/
 ├── application.yml          # Configuración global para todos los servicios
 ├── api-gateway.yml          # Propiedades específicas del API Gateway
 ├── product-service.yml      # Propiedades del servicio de productos
 ├── price-service.yml        # Propiedades del servicio de precios
 └── eureka-server.yml        # Configuración del Discovery Server
```

### Ejemplo básico de un archivo de configuración

**`product-service.yml`**

```yaml
server:
  port: 8081

spring:
  application:
    name: product-service

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

---

## 🧪 Prueba del Config Server

1. Inicia el servicio `config-server`:

   ```bash
   mvn spring-boot:run -pl config-server
   ```

2. Verifica desde el navegador o `curl`:

   ```bash
   http://localhost:8888/product-service/dev
   ```

   Si ves un JSON con las propiedades de configuración, el servidor está funcionando correctamente.

---

## 🧠 Nota para Revisores Técnicos

> En un entorno productivo, el Config Server apuntaría a **un repositorio separado** de configuración.
> En esta demo, la configuración se encuentra en la **rama `config`** del mismo repositorio para simplificar la revisión y mantener el proyecto autocontenido.