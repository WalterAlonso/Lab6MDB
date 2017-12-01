/************************************************************/
Codigo curso: CSOF5302 - 2017.
Nombre curso: DESARROLLO BASADO EN COMPONENTES.
Nombre de la tarea: Laboratorio 6.
Fecha de envío de la tarea: 30/11/2017, 
Autores: 
		Walter Javier Alonso Roa
		Juan Sebastian Paz
/************************************************************/

¨** Descripcion:
Para la ejecucion del sistema se debe realizar:

1. Ir a Ruta de Glassfish
C:\Program Files (x86)\glassfish-4.1.1\glassfish\bin

2. Iniciar Consola de Administración de Glassfish
	Entrar a la consola de comandos
	asadmin

3- Crear ConnectionFactory y Destination Resource de Vendedores
a- Crear ConnectionFactory por consola
  create-jms-resource --restype javax.jms.ConnectionFactory --description "connection factory for Vendedores" --property ClientId=MyID jms/cambioDeCargoTopicFactory
b- Crear Destination Reosurce tipo Topic
  create-jms-resource --restype javax.jms.Topic --property Name=PhysicalTopic jms/cambioDeCargoTopic

4- Crear ConnectionFactory y Destination Resource de Promociones
a- Crear ConnectionFactory por consola
  create-jms-resource --restype javax.jms.ConnectionFactory --description "connection factory for Promocion creada" --property ClientId=MyID jms/promocionCreadaTopicFactory
b- Crear Destination Reosurce tipo Topic
  create-jms-resource --restype javax.jms.Topic --property Name=PhysicalTopic jms/promocionCreadaTopic


** Ambiente de ejecucion:
Este programa fue realizado con JRE 1.8 en S.O 7.


**************** Que cumple el sistema ************************
1. Creacion de vista promociones en el sitio administrador.
2. En la vista del catalogo de muebles en la creacion se adiciona combo con las promociones, segun el tipo de mueble.
3. Al crear una promocion se guarda en el topic, se ejecuta el MDB de ventas y asi mismo el respectivo bean.
4. Al crear un nuevo mueble, se guarda en el topic el mensaje, para que los MDB de Mercadeo y Call center lo tomen y lo envien a sus
respectivos bean.
5. El ejercicio del tutorial asociado al vendedor y su respectivo manejo en el MDB RecursosHumanosMessage..


**************** Problemas en la ejecucion ********************
Con Netbeans pasa algo raro y es que cuando se corre el proyecto puede que no despliegue, esto 
en general nos ha pasado en la mayoria de proyectos, sin embargo no encontramos solucion, que puede hacer:

- apenas abra el proyecto, dar clean and build.
- si esto es fallido:
	- valla a las carpetas "nbproject" del proy raiz, del Ejb y del War (los 3)
	y valla a este archivO: "nbproject\private\private.properties"
	y asegure que las rutas alli dadas, corresponden a las de su maquina local.
- vuelva a abrir el proyecto y de clean and build, con esto ya el proyecto debe compilar y correr sin
problema.