# KILOAPI
Proyecto de desarrollo de una API-REST de gestión para la Campaña del Kilo en el centro Salesianos San Pedro de Sevilla.

## Tecnología y lenguaje utilizado:
Para el desarrollo de la aplicación, se han utilizado los siguientes elementos:
- **Spring Boot** como framework.
- **Java** para el desarrollo del código que atiende las peticiones a la API.
- **H2** para la gestión de la base de datos.
- **JPQL** para las consultas a la base de datos. 

## Entorno de desarrollo y ejecución:
Para el desarrollo del proyecto, se ha utilizado el entorno de desarrollo **IntelliJ IDEA**. Para su ejecución en dicho entorno, abrimos el proyecto y,
en la barra superior, en la parte derecha, en los primeros iconos, seleccionamos sobre **Curent File**, y luego **Edit Configuration**. Al aparecer la nueva
ventana, pulsamos, en el menú superior, en el icono **+**, en el desplegable, seleccionamos **Maven**, y en la opción **Run**, en **Command line**, escribimos
**spring-boot:run** y lo seleccionamos en el menú. Pulsamos en **Aplicar** y **Aceptar**.
Ahora solo tendremos que pulsar el icono de **Play** junto a **kiloapi spring-boot:run**, y, una vez finalice la ejecución en consola, tendremos el proyecto ejecutado
y accesible desde la dirección **http://localhost:8080/** como ruta raíz.

## Pruebas:
Para poder probar la API, tendremos dos vías principales:
- **Documentación del proyecto en Swagger**: Accederemos a través de la ruta **http://localhost:8080/swagger-ui.html**. Aquí, podremos probar todos los endpoints
  disponibles en la API, y ver ejemplos de retorno.
- **Aplicación de Postman**: Dentro del proyecto, se encuentra el archivo **KiloApi.postman_collection.json**. Este archivo, podremos importarlo en las colecciones de Postman,
  en el que hay preparadas una serie de peticiones a todos los posibles métodos de la API. En las peticiones POST y PUT, cuando la seleccionemos, al pulsar en **Body**, podremos
  indicar el cuerpo que se envía en la petición para crear o modificar algún recurso, en formato JSON.
- **Consola H2**: Accederemos a través de la ruta **http://localhost:8080/h2-console**. Podremos acceder a la base de datos para comprobar la información ahí guardada, y, una vez 
  realicemos alguna petición mediante Postman o Swagger, veremos cómo se actualiza esta. En la pantalla principal de acceso, la URL para acceder por defecto será **test**, y
  tendremos que cambiarla por **jdbc:h2:./db/basededatos**.

## Organización del proyecto:
En la carpeta principal nos encontramos diferentes elementos a tener en cuenta:
- **src**: Es la carpeta donde se aloja todo el código fuente utilizado en el desarrollo de la aplicación.
- **KiloApi.postman_collection.json**: Es una colección de Postman, que podremos importar en dicho programa, y que nos permitirá acceder a los distintos endpoints de la API para
  probar todas sus funcionalidades.

## Rutas disponibles:
### TipoAlimento:
- **GET: http://localhost:8080/tipoAlimento/**: Obtiene el listado completo de tipos de alimentos.
- **POST: http://localhost:8080/tipoAlimento/**: Crea un nuevo tipo de alimento.
- **PUT: http://localhost:8080/tipoAlimento/{id}**: Modifica un tipo de alimento, buscado por su ID.
- **DELETE: http://localhost:8080/tipoAlimento/{id}**: Borra un tipo de alimento, buscado por su ID.
- **GET: http://localhost:8080/tipoAlimento/{id}**: Obtiene la información de un tipo de alimento, buscado por su ID.

### Clases:
- **GET: http://localhost:8080/clase/**: Obtiene el listado completo de clases.
- **POST: http://localhost:8080/clase/**: Crea una nueva clase.
- **PUT: http://localhost:8080/clase/{id}**: Modifica una clase, buscada por su ID.
- **DELETE: http://localhost:8080/clase/{id}**: Borra una clase, buscada por su ID.
- **GET: http://localhost:8080/clase/{id}**: Obtiene la información de una clase, buscada por su ID.

### Aportaciones:
- **GET: http://localhost:8080/aportacion/**: Obtiene el listado completo de aportaciones.
- **GET: http://localhost:8080/aportacion/{idClase}**: Obtiene la información de las aportaciones de una clase, buscada por su ID.
- **POST: http://localhost:8080/aportacion/**: Crea una nueva aportación.
- **PUT: http://localhost:8080/aportacion/{id}/linea/{num}/kg/{numKg}**: Modifica los kilos aportados (numKg) en una línea de detalle de la aportación (num), buscada esta última
  por su ID.
- **DELETE: http://localhost:8080/aportacion/{id}**: Borra una aportación, buscada por su ID.
- **DELETE: http://localhost:8080/aportacion/{id}/linea/{num}**: Borra un detalle de una aportación (num), buscada por su ID.
- **GET: http://localhost:8080/aportacion/{id}**: Obtiene la información de una aportación, buscada por su ID.

### Kilos Disponibles:
- **GET: http://localhost:8080/kilosDisponibles/**: Obtiene el listado completo de los kilos disponibles.
- **GET: http://localhost:8080/kilosDisponibles/{idTipoAlimento}**: Obtiene la información de los kilos diposnibles de un tipo de alimento, buscado por su ID.

### Ranking:
- **GET: http://localhost:8080/ranking/**: Obtiene un ranking de clases en función de sus aportaciones.

### Destinatarios:
- **GET: http://localhost:8080/destinatario/**: Obtiene el listado completo de destinatarios.
- **POST: http://localhost:8080/destinatario/**: Crea un nuevo destinatario.
- **PUT: http://localhost:8080/destinatario/{id}**: Modifica un destinatario, buscado por su ID.
- **DELETE: http://localhost:8080/destinatario/{id}**: Borra un destinatario, buscado por su ID.
- **GET: http://localhost:8080/destinatario/{id}**: Obtiene la información de un destinatario, buscado por su ID.
- **GET: http://localhost:8080/destinatario/{id}/detalle**: Obtiene información más detallada de un destinatario, buscado por su ID.

### Cajas:
- **GET: http://localhost:8080/caja/**: Obtiene el listado completo de cajas.
- **POST: http://localhost:8080/caja/**: Crea una nueva caja.
- **POST: http://localhost:8080/caja/{id}/tipo/{idTipoAlimento}/kg/{cantidad}**: Añadir a una caja, buscada por su ID, una cantidad (cantidad) de kilos de un tipo de alimento,
  buscado por su ID (idTipoAlimento).
- **PUT: http://localhost:8080/caja/{id}**: Modifica una caja, buscada por su ID.
- **PUT: http://localhost:8080/caja/{id}/tipo/{idTipoAlimento}/kg/{cantidad}**: Modifica de una caja, buscada por su ID, la cantidad (cantidad) de kilos de un tipo de alimento,
  buscado por su ID (idTipoAlimento)
- **DELETE: http://localhost:8080/caja/{id}**: Borra una caja, buscada por su ID.
- **DELETE: http://localhost:8080/caja/{id}/tipo/{idTipoAlimento}**: Borra de una caja, buscada por su ID, un tipo de alimento que se encuentre en esta.
- **GET: http://localhost:8080/caja/{id}**: Obtiene la información de una caja, buscada por su ID.
- **POST: http://localhost:8080/caja/{id}/destinatario/{idDestinatario}**: Asigna una caja, buscada por su ID, a un destinatario, buscado por su ID (idDestinatario).