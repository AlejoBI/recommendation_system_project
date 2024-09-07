import time
import couchdb

#--------------------------------------------------------------
#Desarrolladores
#Ludy Astrid Agudelo Bravo - 2221008
#Alejandro Bravo Isajar - 2220332
#---------------------------------------------------------------

# Conectar a la base de datos CouchDB
conn_string = "URLBD"
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
        # Actualiza los resultados encontrados
        for row in resultados:
            # Actualizar el documento con el nuevo valor
            row.value.update(documento)
            db.save(row.value)
            return "Documento actualizado correctamente."
    except couchdb.ResourceNotFound:
        print(f"No se encontraron documentos para la llave '{llave}' y el valor '{valor}'.")
        time.sleep(1)
        return ""

def verificar_id_existente(tipo, llave, valor):
    # Utilizar la vista correspondiente según el tipo de documento
    design_doc = f"_design/{tipo}"
    view_name = f"buscar_por_{llave}"

    # Verificar si el diseño y la vista existen
    try:
        db[design_doc]
    except couchdb.ResourceNotFound:
        print(f"El diseño '{design_doc}' y la vista '{view_name}' no existen.")
        time.sleep(1)
        return False

    # Utilizar la vista para buscar el documento por la llave
    try:
        resultados = db.view(f"{tipo}/{view_name}", key=valor)
        # Si hay resultados, el ID ya existe
        if len(list(resultados)) > 0:
            return True
        else:
            return False
    except couchdb.ResourceNotFound:
        print(f"No se encontraron documentos para la llave '{llave}' y el valor '{valor}'.")
        time.sleep(1)
        return False

def verificar_curso_existente(idCurso):
    # Buscar el curso por su ID
    cursos = consultar_documento("cursos", "idCurso", idCurso)
    if cursos:
        # El curso existe
        return True
    else:
        # El curso no existe
        return False

def calificar(tipo, llave, valor, idUsuario, calificacion):
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

    # Verificar la existencia del usuario que califica
    if not verificar_id_existente("usuarios", "idUsuario", idUsuario):
        print("El usuario no existe.")
        return False

    # Verificar la existencia del tutor o curso
    if not verificar_id_existente(tipo, llave, valor):
        print(f" El ID proporcionado no existe.")
        return False
    
    # Utilizar la vista para buscar el documento por la llave
    try:
        resultados = db.view(f"{tipo}/{view_name}", key=valor)
        # Actualizar las calificaciones en los documentos encontrados
        for row in resultados:
            documento = row.value
            documento['calificaciones'][idUsuario] = calificacion
            db.save(documento)
        print(f"{tipo.capitalize()} ha sido calificado correctamente.")
        return ""
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
5. Calificar un "Tutor" o "Curso"
6. Nada
---------------------------------------""")

        opcionA = input("Ingrese una opcion: ")

        if (opcionA == "1"):

            print("""---------------------------------------
Desea consultar:
1. Usuario
2. Tutor
3. Curso
---------------------------------------""")

            opcionB = input("Ingrese una opcion: ")

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
                llave = input("Ingrese la llave de búsqueda (nombre, carrera, semestre, calificacion): ").lower()
                valor = input(f"Ingrese el valor para la llave '{llave}': ")
                tutores = consultar_documento(tipo, llave, valor)
                print("Tutores encontrados:")
                for tutor in tutores:
                    print(tutor)
                    time.sleep(1)

            elif opcionB == "3":
                tipo = "cursos"
                llave = input("Ingrese la llave de búsqueda (nombre, categoria, modalidad, gratuito, certificado, calificacion): ").lower()
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

            print("""---------------------------------------
Desea crear:
1. Usuario
2. Tutor
3. Curso
---------------------------------------""")

            opcionB = input("Ingrese una opcion: ")
            
            if opcionB == "1":
                while True:
                    idUsuario = str(input("Ingrese el id para el usuario: "))
                    # Verificar si el ID ya existe
                    if verificar_id_existente("usuarios", "idUsuario", idUsuario):
                        print("El ID de usuario ingresado ya existe. Por favor, ingrese otro ID.")
                    else:
                        nombre = input("Ingrese el nombre del usuario: ")
                        carrera = input("Ingrese la carrera del usuario: ")
                        semestre = int(input("Ingrese el semestre del usuario: "))
                        idCursos = []
                        while True:
                            idCurso = str(input("Ingrese el ID del curso al que está inscrito el usuario (o 'fin' para terminar): "))
                            if idCurso.lower() == 'fin':
                                break
                            if verificar_curso_existente(idCurso):
                                idCursos.append(idCurso)
                            else:
                                print(f"El curso con ID '{idCurso}' no existe. Por favor, ingrese un ID de curso válido.")
                        if idCursos:
                            usuario = {
                                'idUsuario': idUsuario,
                                'tipo': 'usuario',
                                'nombre': nombre,
                                'carrera': carrera,
                                'semestre': semestre,
                                'idCursos': idCursos
                            }
                            nuevo_usuario = crear_documento('usuarios', usuario)
                            print(f"Usuario creado con ID: {nuevo_usuario}")
                            time.sleep(1)
                            break
                        else:
                            print("No se ingresaron cursos válidos. Por favor, inténtelo nuevamente.")

            elif opcionB == "2":
                while True:
                    idTutor = str(input("Ingrese el id para el tutor: "))
                    # Verificar si el ID ya existe
                    if verificar_id_existente("tutores", "idTutor", idTutor):
                        print("El ID de tutor ingresado ya existe. Por favor, ingrese otro ID.")
                    else:
                        nombre = input("Ingrese el nombre del tutor: ")
                        carrera = input("Ingrese la carrera del tutor: ")
                        semestre = int(input("Ingrese el semestre del tutor: "))
                        idCursos = []
                        while True:
                            idCurso = str(input("Ingrese el ID del curso que imparte el tutor (o 'fin' para terminar): "))
                            if idCurso.lower() == 'fin':
                                break
                            if verificar_curso_existente(idCurso):
                                idCursos.append(idCurso)
                            else:
                                print(f"El curso con ID '{idCurso}' no existe. Por favor, ingrese un ID de curso válido.")
                        if idCursos:
                            tutor = {
                                'idTutor': idTutor,
                                'tipo': 'tutor',
                                'nombre': nombre,
                                'carrera': carrera,
                                'semestre': semestre,
                                'idCursos': idCursos,
                                'calificaciones': {}
                            }
                            nuevo_tutor = crear_documento('tutores', tutor)
                            print(f"Tutor creado con ID: {nuevo_tutor}")
                            time.sleep(1)
                            break
                        else:
                            print("No se ingresaron cursos válidos. Por favor, inténtelo nuevamente.")

            elif opcionB == "3":
                while True:
                    idCurso = str(input("Ingrese el id para el curso: "))
                    # Verificar si el ID ya existe
                    if verificar_id_existente("cursos", "idCurso", idCurso):
                        print("El ID de curso ingresado ya existe. Por favor, ingrese otro ID.")
                    else:
                        nombre = input("Ingrese el nombre del curso: ")
                        categoria = input("Ingrese la categoría del curso (artes, humanidades, ciencias básicas, tecnologia): ")
                        modalidad = input("Ingrese la modalidad del curso (presencial, remoto): ")
                        gratuito = input("¿El curso es gratuito? (V/F): ").upper() == 'V'
                        precio = float(input("Ingrese el precio del curso (0 si es gratuito): "))
                        duracion = int(input("Ingrese la duración del curso (en horas): "))
                        certificado = input("¿El curso otorga certificado? (V/F): ").upper() == 'V'
                        curso = {
                            'idCurso': idCurso,
                            'tipo': 'curso',
                            'nombre': nombre,
                            'categoria': categoria,
                            'modalidad': modalidad,
                            'gratuito': gratuito,
                            'precio': precio,
                            'duracion': duracion,
                            'certificado': certificado,
                            'calificaciones': {}
                        }
                        nuevo_curso = crear_documento('cursos', curso)
                        print(f"Curso creado con ID: {nuevo_curso}")
                        time.sleep(1)
                        break

            else:
                print("Opción inválida. Intente nuevamente.")
                time.sleep(1)

        elif (opcionA == "3"):

            print("""---------------------------------------
Desea eliminar:
1. Usuario
2. Tutor
3. Curso
---------------------------------------""")

            opcionB = input("Ingrese una opcion: ")
            
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
            
            print("""---------------------------------------
Desea actualizar:
1. Usuario
2. Tutor
3. Curso
---------------------------------------""")

            opcionB = input("Ingrese una opcion: ")
            
            if opcionB == "1":
                idUsuario = str(input("Ingrese el id para el usuario: "))
                nombre = input("Ingrese el nombre del usuario: ")
                carrera = input("Ingrese la carrera del usuario: ")
                semestre = int(input("Ingrese el semestre del usuario: "))
                idCursos = []
                while True:
                    idCurso = str(input("Ingrese el ID del curso al que está inscrito el usuario (o 'fin' para terminar): "))
                    if idCurso.lower() == 'fin':
                        break
                    if verificar_curso_existente(idCurso):
                        idCursos.append(idCurso)
                    else:
                        print(f"El curso con ID '{idCurso}' no existe. Por favor, ingrese un ID de curso válido.")
                if idCursos:
                    usuario = {
                        'idUsuario': idUsuario,
                        'tipo': 'usuario',
                        'nombre': nombre,
                        'carrera': carrera,
                        'semestre': semestre,
                        'idCursos': idCursos
                    }
                    # Llamar a la función para actualizar el usuario
                    usuario_actualizado = actualizar_documento_por_id('usuarios', 'idUsuario', idUsuario, usuario)
                    print (usuario_actualizado)
                    time.sleep(1)
                else:
                    print("No se ingresaron cursos válidos. Por favor, inténtelo nuevamente.")

            elif opcionB == "2":
                idTutor = str(input("Ingrese el id para el tutor: "))
                nombre = input("Ingrese el nombre del tutor: ")
                carrera = input("Ingrese la carrera del tutor: ")
                semestre = int(input("Ingrese el semestre del tutor: "))
                idCursos = []
                while True:
                    idCurso = str(input("Ingrese el ID del curso que imparte el tutor (o 'fin' para terminar): "))
                    if idCurso.lower() == 'fin':
                        break
                    if verificar_curso_existente(idCurso):
                        idCursos.append(idCurso)
                    else:
                        print(f"El curso con ID '{idCurso}' no existe. Por favor, ingrese un ID de curso válido.")
                if idCursos:
                    tutor = {
                        'idTutor': idTutor,
                        'tipo': 'tutor',
                        'nombre': nombre,
                        'carrera': carrera,
                        'semestre': semestre,
                        'idCursos': idCursos,
                        'calificacion': calificacion

                    }
                    # Llamar a la función para actualizar el tutor
                    tutor_actualizado = actualizar_documento_por_id('tutores', 'idTutor', idTutor, tutor)
                    print (tutor_actualizado)
                    time.sleep(1)
                else:
                    print("No se ingresaron cursos válidos. Por favor, inténtelo nuevamente.")

            elif opcionB == "3":
                idCurso = str(input("Ingenese el id para el curso: "))
                nombre = input("Ingrese el nombre del curso: ")
                categoria = input("Ingrese la categoría del curso (artes, humanidades, ciencias básicas, tecnologia): ")
                modalidad = input("Ingrese la modalidad del curso (presencial, remoto): ")
                gratuito = input("¿El curso es gratuito? (V/F): ").upper() == 'V'
                precio = float(input("Ingrese el precio del curso (0 si es gratuito): "))
                duracion = int(input("Ingrese la duración del curso (en horas): "))
                certificado = input("¿El curso otorga certificado? (V/F): ").upper() == 'V'
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
                    'calificacion': calificacion
                }
                # Llamar a la función para actualizar el usuario
                curso_actualizado = actualizar_documento_por_id('cursos', 'idCurso', idCurso, curso)
                print (curso_actualizado)
                time.sleep(1)
            
            else:
                print("Opción inválida. Intente nuevamente.")
                time.sleep(1)

        elif (opcionA == "5"):
            
            print("""---------------------------------------
Desea calificar:
1. Tutor
2. Curso
---------------------------------------""")
            
            opcionB = input("Ingrese una opción: ")

            if opcionB == "1":
                tipo = "tutores"
                llave = "idTutor"
                valor = str(input("Ingrese el ID del tutor a calificar: "))
                idUsuario = input("Ingrese el ID del usuario que está calificando: ")
                calificacion = float(input("Ingrese la calificación (0.0 - 5.0): "))
                resultado = calificar(tipo, llave, valor, idUsuario, calificacion)
                print(resultado)

            elif opcionB == "2":
                tipo = "cursos"
                llave = "idCurso"
                valor = str(input("Ingrese el ID del curso a calificar: "))
                idUsuario = input("Ingrese el ID del usuario que está calificando: ")
                calificacion = float(input("Ingrese la calificación (0.0 - 5.0): "))
                resultado = calificar(tipo, llave, valor, idUsuario, calificacion)
                print(resultado)

            else:
                print("Opción inválida. Intente nuevamente.")
                time.sleep(1)

        else:
                print("Hasta Luego!")
                exit()

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
