import time
import couchdb

# Conectar a la base de datos CouchDB
conn_string = "http://AlejandroBI:ABI5846s@localhost:5984"
server = couchdb.Server(conn_string)
db_name = "recommendation_system"
db = server[db_name]

# Consulta de Usuario, Tutor y Curso por alguna llave (no necesariamente el id)
def consultar_documento(tipo, llave, valor):
    # Utilizar la vista correspondiente según el tipo de documento
    design_doc = f"_design/{tipo}"
    view_name = f"buscar_por_{llave}"

    # Verificar si el diseño y la vista existen
    try:
        db[design_doc]
    except couchdb.ResourceNotFound:
        print(f"El diseño '{design_doc}' y la vista '{view_name}' no existen.")
        time.sleep(1)
        return ""

    # Utilizar la vista para buscar el documento por la llave
    try:
        resultados = db.view(f"{tipo}/{view_name}", key=valor)
        # Retornar los documentos encontrados
        return [row.value for row in resultados]
    except couchdb.ResourceNotFound:
        print(f"No se encontraron documentos para la llave '{llave}' y el valor '{valor}'.")
        time.sleep(1)
        return ""

def crear_documento(coleccion, documento):
    nuevo_documento = db.save(documento)
    return nuevo_documento

def menu():
    while True:
        print("""---------------------------------------
Seleccione una opcion:
1. Consultar Usuario
2. Consultar Tutor
3. Consultar Curso
4. Crear Usuario
5. Crear Tutor
6. Crear Curso
7. Salir
---------------------------------------""")

        opcion = input("Ingrese una opcion: ")

        if opcion == "1":
            tipo = "usuarios"
            llave = str(input("Ingrese la llave de búsqueda (nombre, carrera, semestre): ")).lower()
            valor = input(f"Ingrese el valor para la llave '{llave}': ")
            usuarios = consultar_documento(tipo, llave, valor)
            print("Usuarios encontrados:")
            for usuario in usuarios:
                print(usuario)
                time.sleep(1)

        elif opcion == "2":
            tipo = "tutores"
            llave = input("Ingrese la llave de búsqueda (nombre, carrera, semestre, calificacion_promedio): ")
            valor = input(f"Ingrese el valor para la llave '{llave}': ")
            tutores = consultar_documento(tipo, llave, valor)
            print("Tutores encontrados:")
            for tutor in tutores:
                print(tutor)
                time.sleep(1)

        elif opcion == "3":
            tipo = "cursos"
            llave = input("Ingrese la llave de búsqueda (nombre, categoria, modalidad, gratuito, certificado, calificacion_promedio): ")
            valor = input(f"Ingrese el valor para la llave '{llave}': ")
            cursos = consultar_documento(tipo, llave, valor)
            print("Cursos encontrados:")
            for curso in cursos:
                print(curso)
                time.sleep(1)

        elif opcion == "4":
            idUsuario = 0
            nombre = input("Ingrese el nombre del usuario: ")
            carrera = input("Ingrese la carrera del usuario: ")
            semestre = int(input("Ingrese el semestre del usuario: "))
            usuario = {
                'idUsuario':idUsuario,
                'tipo':'usuario',
                'nombre':nombre,
                'carrera':carrera,
                'semestre':semestre
            }
            nuevo_usuario = crear_documento('usuarios', usuario)
            print(f"Usuario creado con ID: {nuevo_usuario}")
            time.sleep(1)

        elif opcion == "5":
            idTutor = 0
            nombre = input("Ingrese el nombre del tutor: ")
            carrera = input("Ingrese la carrera del tutor: ")
            semestre = int(input("Ingrese el semestre del tutor: "))
            calificacion_promedio = float(input("Ingrese la calificación promedio del tutor: "))
            tutor = {
                'idTutos':idTutor,
                'tipo':'tutor',
                'nombre':nombre,
                'carrera':carrera,
                'semestre':semestre,
                'calificacion_promedio':calificacion_promedio
            }
            nuevo_tutor = crear_documento('tutores', tutor)
            print(f"Tutor creado con ID: {nuevo_tutor}")
            time.sleep(1)

        elif opcion == "6":
            idCurso = 0
            nombre = input("Ingrese el nombre del curso: ")
            categoria = input("Ingrese la categoría del curso (artes, humanidades, ciencias básicas, tecnologia): ")
            modalidad = input("Ingrese la modalidad del curso (presencial, remoto): ")
            gratuito = input("¿El curso es gratuito? (V/F): ").upper() == 'V'
            precio = float(input("Ingrese el precio del curso (0 si es gratuito): "))
            duracion = int(input("Ingrese la duración del curso (en horas): "))
            certificado = input("¿El curso otorga certificado? (V/F): ").upper() == 'V'
            calificacion_promedio = float(input("Ingrese la calificación promedio del curso (0.0 a 5.0): "))
            curso = {
                'idCurso':idCurso,
                'tipo':'curso',
                'nombre':nombre,
                'categoria':categoria,
                'modalidad':modalidad,
                'gratuito':gratuito,
                'precio':precio,
                'duracion':duracion,
                'certificado':certificado,
                'calificacion_promedio':calificacion_promedio
            }
            nuevo_curso = crear_documento('cursos', curso)
            print(f"Curso creado con ID: {nuevo_curso}")
            time.sleep(1)

        elif opcion == "7":
            print("¡Hasta luego!")
            exit()

        else:
            print("Opción inválida. Intente nuevamente.")
            time.sleep(1)
            
#---------------------------------------------------
# INICIO DE TODO EL SISTEMA
while True:
    if db_name in server:
        print("Conectado con la BD")

        if __name__ == '__main__':
            menu()

    else:
        db = server.create(db_name)
        print("La base de datos no existe. Creando...")

#---------------------------------------------------
