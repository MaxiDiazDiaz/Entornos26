# Informe Técnico – Incidencia en formulario de contacto

## 1. Descripción del problema

La aplicación permite procesar un formulario de contacto incluso cuando los campos obligatorios (nombre y email) están vacíos.

Aunque existe una validación del email, el sistema no detiene la ejecución cuando los datos son inválidos. Como resultado, el formulario se procesa igualmente, generando una simulación de envío con información incorrecta o vacía.

---

## 2. Cómo se reproduce

1. Ejecutar la clase `MainApp`.

2. En el método `main`, se ejecuta la siguiente instrucción:


contactController.submitContactForm("", "");

3. El sistema muestra en consola que el formulario ha sido procesado, aunque:

    - El nombre está vacío.

    - El email está vacío.


Resultado observado:  
El formulario se procesa sin errores aparentes.

Resultado esperado:  
El sistema debería detectar que los campos están vacíos y detener el procesamiento.

---

## 3. Análisis técnico (qué ocurre internamente)

El flujo de ejecución es el siguiente:

### 1️⃣ Desde `MainApp`

contactController.submitContactForm("", "");

Se envían dos cadenas vacías como parámetros (`name` y `email`).

---

### 2️⃣ En `ContactController`

public void submitContactForm(String name, String email) {  
Logger.log("Recibiendo formulario de contacto...");  
ContactForm form = new ContactForm(name, email);  
service.processForm(form);  
Logger.log("Fin de submitContactForm");  
}

- Se crea un objeto `ContactForm` con los valores recibidos.

- No se realiza ninguna validación en el controlador.

- Se delega directamente la lógica al `ContactService`.


---

### 3️⃣ En `ContactService`

if (Validator.validateEmail(form.getEmail())) {  
Logger.log("Email es válido!");  
}

Logger.log("Procesando formulario para: " + form.getName());  
Logger.log("Mensaje enviado a: " + form.getName().toUpperCase());

El método `validateEmail` únicamente comprueba si el email contiene `"@"`.

Cuando el email está vacío:

- `validateEmail("")` devuelve `false`.

- El bloque `if` no se ejecuta.

- Sin embargo, el método continúa ejecutándose.

- El formulario se procesa igualmente.


No existe:

- Condición que bloquee el flujo si la validación falla.

- Retorno anticipado (`return`).

- Lanzamiento de excepción.

- Validación del campo `name`.


---

### 🔎 Conclusión del análisis interno

El sistema sí ejecuta la validación, pero no utiliza el resultado negativo para detener el procesamiento.

El problema no está en que la validación no funcione, sino en que su resultado no afecta al flujo del programa.

---

## 4. Causa raíz

La causa raíz es un error en el control del flujo lógico.

Existe validación, pero no existe una acción cuando la validación falla.  
El método `processForm` continúa su ejecución independientemente del resultado del `if`.

En términos técnicos, la validación no está integrada correctamente en la lógica de negocio.

---

## 5. Propuesta de solución (sin modificar aún)

Se propone:

- Implementar validación completa de los campos obligatorios (nombre y email).

- Introducir control de flujo que detenga la ejecución si la validación falla.

- Evitar que el procesamiento continúe cuando los datos sean inválidos.

- Mantener la responsabilidad de validación centralizada (por ejemplo, en `Validator`).