import couchdb

# Conectar a la base de datos CouchDB
conn_string = "http://AlejandroBI:ABI5846s@localhost:5984"
server = couchdb.Server(conn_string)
db_name = "recommendation_system"
db = server[db_name]

