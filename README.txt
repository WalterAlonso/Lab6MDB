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

