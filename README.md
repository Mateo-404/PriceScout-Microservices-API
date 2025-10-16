# 💻 PriceScout API: Comparador de Precios de Hardware

**PriceScout API** es un sistema de microservicios diseñado para demostrar habilidades avanzadas en la creación de sistemas distribuidos, resilientes y escalables con **Spring Cloud**. El proyecto simula un comparador de precios de hardware que centraliza información de múltiples proveedores para ofrecer al usuario la mejor opción en tiempo real.

-----

## 🎯 Objetivo y Valor del Proyecto

El principal desafío en los e-commerce es la dependencia de servicios externos (proveedores, pasarelas de pago, etc.). **PriceScout API** ataca este problema directamente, implementando una arquitectura que no solo sobrevive a fallos de terceros, sino que además mantiene un alto rendimiento y es fácil de gestionar.

A través de este proyecto, demuestro dominio en:

  * **Arquitecturas de Microservicios:** Diseño y orquestación de servicios independientes.
  * **Tolerancia a Fallos:** Creación de sistemas que no se caen en cascada.
  * **Gestión Centralizada:** Configuración de múltiples entornos sin cambiar el código.
  * **Despliegue Contenerizado:** Empaquetado de toda la aplicación para un despliegue universal.

-----

## 🏗️ Arquitectura del Sistema

El sistema utiliza una arquitectura de microservicios desacoplada donde cada componente tiene una responsabilidad única. Esto facilita la escalabilidad, el mantenimiento y el despliegue independiente de cada parte del sistema.


### Componentes Clave:

  * **`API Gateway`**: Punto de entrada único para todas las peticiones. Enruta, asegura y monitorea el tráfico hacia los servicios internos.
  * **`Discovery Server (Eureka)`**: Permite que los microservicios se encuentren dinámicamente en la red, evitando la configuración manual de IPs y puertos.
  * **`Config Server`**: Centraliza la configuración de todos los microservicios en un repositorio Git externo. Permite cambios de configuración en caliente sin necesidad de redesplegar.
  * **`Product-Service`**: Gestiona la información base de los productos (ID, nombre, descripción).
  * **`Price-Service`**: El núcleo de la lógica. Orquesta las llamadas a las APIs externas (simuladas), implementa patrones de resiliencia y caché para optimizar las respuestas.

-----

## ✨ Patrones de Diseño y Habilidades Demostradas

Esta tabla conecta cada tecnología con la habilidad profesional que demuestra.

| Patrón / Tecnología | Habilidad Demostrada |
| :--- | :--- |
| **Circuit Breaker & Retry (Resilience4J)** | **Diseño de Sistemas Resilientes:** Garantiza que la API principal siga funcionando incluso si uno de los proveedores externos falla, devolviendo una respuesta controlada y evitando caídas en cascada. |
| **Centralized Configuration (Config Server)** | **Gestión de Entornos y DevOps:** Capacidad para manejar configuraciones para entornos de desarrollo, staging y producción desde una única fuente de verdad (Git), una práctica estándar en CI/CD. |
| **Caching (Spring Cache / Redis)** | **Optimización de Rendimiento:** Reduce la latencia y la carga sobre servicios externos al almacenar en memoria resultados frecuentes, demostrando conocimiento en la optimización de recursos. |
| **Service Discovery (Eureka)** | **Sistemas Distribuidos Dinámicos:** Implementación de una arquitectura elástica donde los servicios pueden escalar horizontalmente (añadir o quitar instancias) sin intervención manual. |
| **API Gateway (Spring Cloud Gateway)** | **Abstracción y Seguridad:** Creación de una fachada única que simplifica la interacción del cliente con el sistema y sirve como punto de control para la seguridad y el enrutamiento. |
| **Docker & Docker Compose** | **Contenerización y Despliegue:** Habilidad para empaquetar una aplicación compleja y todas sus dependencias en contenedores portables, garantizando un despliegue consistente en cualquier entorno. |

-----

## 🚀 Levantando el Proyecto Localmente

Sigue estos pasos para tener todo el ecosistema funcionando en tu máquina en minutos.

### **Pre-requisitos**

  * [Java 21 (JDK)](https://www.oracle.com/java/technologies/downloads/)
  * [Apache Maven](https://maven.apache.org/download.cgi)
  * [Docker y Docker Compose](https://www.docker.com/products/docker-desktop/)

### **Pasos de Ejecución**

1.  **Clonar el Repositorio:**

    ```bash
    git clone https://github.com/Mateo-404/PriceScout-Microservices-API
    cd PriceScout-Microservices-API
    ```

2.  **Construir las Imágenes de Docker:**
    Este comando compilará cada microservicio con Maven y luego creará su imagen de Docker correspondiente.

    ```bash
    docker-compose build
    ```

3.  **Iniciar el Ecosistema:**
    Levanta todos los contenedores en segundo plano (`-d`).

    ```bash
    docker-compose up -d
    ```

4.  **Verificar los Servicios:**

      * **Dashboard de Eureka:** Espera aproximadamente un minuto para que todos los servicios se registren. Deberías ver `API-GATEWAY`, `PRODUCT-SERVICE` y `PRICE-SERVICE` en la lista.
          * ➡️ **URL:** `http://localhost:8761`
      * **Punto de Entrada (API Gateway):** Todas las peticiones al sistema se hacen a través de esta URL.
          * ➡️ **URL:** `http://localhost:8080`

-----

## 🧪 Cómo Probar la API

Todas las pruebas se realizan a través del **API Gateway**.

### 1\. Búsqueda de Precios (Caso Ideal)

Esta es la funcionalidad principal. Consulta el producto y obtiene los precios de todos los proveedores simulados.

```bash
# Realiza una búsqueda para el producto con ID 1
curl -X GET http://localhost:8080/api/v1/search/1
```

### 2\. Demostración de Resiliencia (Circuit Breaker)

Aquí es donde se ve la magia. Vamos a simular que uno de los proveedores se cae.

  * **Paso 1: Detén uno de los contenedores que simula una tienda externa.**
    (Supongamos que tienes contenedores simulando tiendas, si no, puedes detener `price-service` para ver el efecto en el Gateway).

  * **Paso 2: Vuelve a ejecutar la misma petición de búsqueda.**

    ```bash
    curl -X GET http://localhost:8080/api/v1/search/1
    ```

  * **Resultado Esperado:** En lugar de un error `500` o un timeout, el sistema responderá inmediatamente con los precios de los proveedores que sí funcionan y un mensaje de advertencia (el `fallback method`), demostrando que el sistema sigue operativo.

### Endpoints Disponibles

| Funcionalidad | Método HTTP | Ruta a través del Gateway |
| :--- | :--- | :--- |
| **Búsqueda (API Principal)** | `GET` | `/api/v1/search/{product_id}` |
| **Crear Producto (CRUD)** | `POST` | `/api/v1/products/create` |
| **Ver Estado del Config Server**| `GET` | `/config-server/actuator/health` |

-----

## 📚 Notas y Documentación Adicional

Documenté mi proceso de aprendizaje y las decisiones técnicas de este proyecto en RemNote. Esta es una muestra de mi compromiso con la documentación y el conocimiento compartido.

  * **[Apuntes Técnicos del Proyecto (RemNote)](https://remnote.com/a/SpringBoot-Microservicios/68efc3dc45c39cfb331f629d)**

-----

## 👤 Sobre Mí y Contacto

¡Hola\! Soy **Mateo Gariboglio**, un desarrollador apasionado por construir soluciones escalables y eficientes. Este proyecto es una muestra de mis habilidades y mi enfoque en la calidad del software.

¡No dudes en contactarme\!

  * **LinkedIn:** [https://www.linkedin.com/in/mateo-gariboglio](https://www.linkedin.com/in/mateo-gariboglio)
  * **Portfolio Web:** [http://mateogariboglio.is-a.dev/](http://mateogariboglio.is-a.dev/)
  * **GitHub:** [https://github.com/Mateo-404](https://github.com/Mateo-404)
