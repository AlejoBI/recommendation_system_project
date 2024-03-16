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

def eliminar_documento_por_id(tipo, llave, valor):
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
        # Obtener el documento utilizando la vista
        resultados = db.view(f"{tipo}/{view_name}", key=valor)
        # Eliminamos los resultados encontrados uno por uno
        for row in resultados:
            db.delete(db[row.id])
        return "Documentos eliminados correctamente."
    except couchdb.ResourceNotFound:
        print(f"No se encontraron documentos para la llave '{llave}' y el valor '{valor}'.")
        time.sleep(1)
        return ""
    
def actualizar_documento_por_id(tipo, llave, valor, documento):
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
        # Obtener el documento utilizando la vista
        resultados = db.view(f"{tipo}/{view_name}", key=valor)
        # Actualiza los resultados encnotrados
        for row in resultados:
            # Actualizar el documento con el nuevo valor
            row.value.update(documento)
            db.save(row.value)
            return "Documento actualizado correctamente."
    except couchdb.ResourceNotFound:
        print(f"No se encontraron documentos para la llave '{llave}' y el valor '{valor}'.")
        time.sleep(1)
        return ""

def menu():
    while True:

        print("""---------------------------------------
Seleccione que desea hacer:
1. Consultar
2. Crear
3. Eliminar
4. Actualizar
5. Nada
---------------------------------------""")

        opcionA = input("Ingrese una opcion: ")

        if (opcionA == "1"):

            opcionB = str(input("""Desea consultar:
                            1. Usuario
                            2. Tutor
                            3. Curso"""))

            if opcionB == "1":
                tipo = "usuarios"
                llave = str(input("Ingrese la llave de búsqueda (nombre, carrera, semestre): ")).lower()
                valor = input(f"Ingrese el valor para la llave '{llave}': ")
                usuarios = consultar_documento(tipo, llave, valor)
                print("Usuarios encontrados:")
                for usuario in usuarios:
                    print(usuario)
                    time.sleep(1)

            elif opcionB == "2":
                tipo = "tutores"
                llave = input("Ingrese la llave de búsqueda (nombre, carrera, semestre, calificacion_promedio): ").lower()
                valor = input(f"Ingrese el valor para la llave '{llave}': ")
                tutores = consultar_documento(tipo, llave, valor)
                print("Tutores encontrados:")
                for tutor in tutores:
                    print(tutor)
                    time.sleep(1)

            elif opcionB == "3":
                tipo = "cursos"
                llave = input("Ingrese la llave de búsqueda (nombre, categoria, modalidad, gratuito, certificado, calificacion_promedio): ").lower()
                valor = input(f"Ingrese el valor para la llave '{llave}': ")
                cursos = consultar_documento(tipo, llave, valor)
                print("Cursos encontrados:")
                for curso in cursos:
                    print(curso)
                    time.sleep(1)

            else:
                print("Opción inválida. Intente nuevamente.")
                time.sleep(1)

        elif opcionA == "2":

            opcionB = str(input("""Desea consultar:
                            1. Usuario
                            2. Tutor
                            3. Curso"""))
            
            if opcionB == "1":
                idUsuario = str(input("Ingenese el id para el usuario: "))
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

            elif opcionB == "2":
                idTutor = str(input("Ingenese el id para el tutor: "))
                nombre = input("Ingrese el nombre del tutor: ")
                carrera = input("Ingrese la carrera del tutor: ")
                semestre = int(input("Ingrese el semestre del tutor: "))
                calificacion_promedio = float(input("Ingrese la calificación promedio del tutor: "))
                tutor = {
                    'idTutor':idTutor,
                    'tipo':'tutor',
                    'nombre':nombre,
                    'carrera':carrera,
                    'semestre':semestre,
                    'calificacion_promedio':calificacion_promedio
                }
                nuevo_tutor = crear_documento('tutores', tutor)
                print(f"Tutor creado con ID: {nuevo_tutor}")
                time.sleep(1)

            elif opcionB == "3":
                idCurso = str(input("Ingenese el id para el curso: "))
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

            else:
                print("Opción inválida. Intente nuevamente.")
                time.sleep(1)

        elif (opcionA == "3"):

            opcionB = str(input("""Desea consultar:
                            1. Usuario
                            2. Tutor
                            3. Curso"""))
            
            if opcionB == "1":
                tipo = "usuarios"
                llave = "idUsuario"
                valor = str(input("Ingrese la llave de (Id del usuario): ")).upper()
                usuario = eliminar_documento_por_id(tipo, llave, valor)
                print (usuario)

            elif opcionB == "2":
                tipo = "tutores"
                llave = "idTutor"
                valor = str(input("Ingrese la llave de (Id del tutor): ")).upper()
                tutor = eliminar_documento_por_id(tipo, llave, valor)
                print (tutor)

            elif opcionB == "3":
                tipo = "cursos"
                llave = "idCurso"
                valor = str(input("Ingrese la llave de (Id del curso): ")).upper()
                curso = eliminar_documento_por_id(tipo, llave, valor)
                print (curso)

            else:
                print("Opción inválida. Intente nuevamente.")
                time.sleep(1)

        elif (opcionA == "4"):
            
            opcionB = str(input("""Desea actualizar:
                            1. Usuario
                            2. Tutor
                            3. Curso"""))
            
            if opcionB == "1":
                idUsuario = str(input("Ingrese el id para el usuario: "))
                nombre = input("Ingrese el nombre del usuario: ")
                carrera = input("Ingrese la carrera del usuario: ")
                semestre = int(input("Ingrese el semestre del usuario: "))
                usuario = {
                    'idUsuario': idUsuario,
                    'tipo': 'usuario',
                    'nombre': nombre,
                    'carrera': carrera,
                    'semestre': semestre
                }
                # Llamar a la función para actualizar el usuario
                usuario_actualizado = actualizar_documento_por_id('usuarios', 'idUsuario', idUsuario, usuario)
                print (usuario_actualizado)
                time.sleep(1)

            elif opcionB == "2":
                idTutor = str(input("Ingenese el id para el tutor: "))
                nombre = input("Ingrese el nombre del tutor: ")
                carrera = input("Ingrese la carrera del tutor: ")
                semestre = int(input("Ingrese el semestre del tutor: "))
                calificacion_promedio = float(input("Ingrese la calificación promedio del tutor: "))
                tutor = {
                    'idTutor':idTutor,
                    'tipo':'tutor',
                    'nombre':nombre,
                    'carrera':carrera,
                    'semestre':semestre,
                    'calificacion_promedio':calificacion_promedio
                }
                # Llamar a la función para actualizar el usuario
                tutor_actualizado = actualizar_documento_por_id('tutores', 'idTutor', idTutor, tutor)
                print (tutor_actualizado)
                time.sleep(1)

            elif opcionB == "3":
                idCurso = str(input("Ingenese el id para el curso: "))
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
                # Llamar a la función para actualizar el usuario
                curso_actualizado = actualizar_documento_por_id('cursos', 'idCurso', idCurso, curso)
                print (curso_actualizado)
                time.sleep(1)
            
            else:
                print("Opción inválida. Intente nuevamente.")
                time.sleep(1)

        else:
                print("Hasta Luego!")
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
