# Informe Técnico – Incidencia en formulario de contacto

## 1. Descripción del problema

La aplicación permite procesar un formulario de contacto incluso cuando los campos obligatorios (nombre y email) están vacíos.

Aunque existe una validación del email, el sistema no detiene la ejecución cuando los datos son inválidos. Como resultado, el formulario se procesa igualmente, generando una simulación de envío con información incorrecta o vacía.

---

## 2. Cómo se reproduce

1. Ejecutar la clase `MainApp`.

2. En el método `main`, se ejecuta la siguiente instrucción:

`contactController.submitContactForm("", "");`

3. El sistema muestra en consola que el formulario ha sido procesado, aunque el nombre y el email estan vacíos. COmo resultado el formulario se procesa sin errores aparentes, cuando en realidad el sistema debería detectar que los campos están vacíos y detener el procesamiento.

---

## 3. Análisis técnico

El flujo de ejecución es el siguiente:

### Desde `MainApp` el código:

`contactController.submitContactForm("", "");`

Envía dos cadenas vacías como parámetros (`name` y `email`).

---

### En `ContactController`:

`public void submitContactForm(String name, String email) {  
Logger.log("Recibiendo formulario de contacto...");  
ContactForm form = new ContactForm(name, email);  
service.processForm(form);  
Logger.log("Fin de submitContactForm");  
}`

Crea un objeto `ContactForm` con los valores recibidos.
Y no se realiza ninguna validación en el controlador, sino que se delega directamente la lógica al `ContactService`.

---

### En `ContactService` el código If:

`if (Validator.validateEmail(form.getEmail())) {  
Logger.log("Email es válido!");  
}
Logger.log("Procesando formulario para: " + form.getName());  
Logger.log("Mensaje enviado a: " + form.getName().toUpperCase());`

El método `validateEmail` únicamente comprueba si el email contiene `"@"`. pero el email está vacío, por lo que `validateEmail("")` devuelve `false`. y el bloque `if` no se ejecuta, por lo que el resto del método continúa ejecutándose, y el formulario se procesa igualmente.

En el If no existe una condición que bloquee el flujo si la validación falla, ni retorno anticipado (`return`), ni lanzamiento de excepción.

Además, tampoco existe una validación del campo `name`.

---

### Conclusión del análisis interno

El sistema sí ejecuta la validación, pero no utiliza el resultado negativo para detener el procesamiento.

El problema no está en que la validación no funcione, sino en que su resultado no afecta al flujo del programa.

---

## 4. Causa raíz

La causa raíz es un error en el control del flujo lógico generado en el If.

Existe validación, pero no existe una acción cuando la validación falla.  
El método `processForm` continúa su ejecución independientemente del resultado del `if`.

---

## 5. Propuesta de solución

Se propone:

- Implementar validación completa de los campos obligatorios (nombre y email).
- Introducir control de flujo que detenga la ejecución si la validación falla.
- Evitar que el procesamiento continúe cuando los datos sean inválidos.
- Mantener la responsabilidad de validación centralizada (por ejemplo, en `Validator`).
