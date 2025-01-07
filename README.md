# Proyecto SofBank

Este proyecto tiene como finalidad emular un sistema bancario básico, aplicando principios de diseño orientado a objetos (SOLID) y patrones de diseño como Singleton, Factory, Strategy y Observer. El objetivo es crear un sistema escalable y flexible para la creación y gestión de cuentas bancarias.

## Estructura del Proyecto

El proyecto sigue una estructura bien definida para mantener una separación clara de responsabilidades:

- **`com.SofBank.bank.domains.aplicacion`**: Contiene la clase principal `Main`, que actúa como el punto de entrada del programa.
- **`com.SofBank.bank.domains.modelo`**: Contiene las clases que representan las entidades del negocio, como:
    - `CuentaBancaria`: Clase base para las cuentas bancarias.
    - `CuentaAhorro`: Subclase que implementa una cuenta de ahorro.
    - `CuentaCorriente`: Subclase que implementa una cuenta corriente.
    - `TipoCuenta`: Enum que define los tipos de cuentas disponibles.
- **`com.SofBank.bank.domains.Fabric`**: Contiene las clases relacionadas con el patrón **Factory**, incluyendo:
    - `CtaBancariaFactory`: Clase abstracta base para las fábricas de cuentas.
    - `CtaAhorroFactory`: Implementación de fábrica para cuentas de ahorro.
    - `CtaCorrienteFactory`: Implementación de fábrica para cuentas corrientes.
- **`com.SofBank.bank.domains.Strategy`**: Contiene las clases relacionadas con el patrón **Strategy**, como:
    - `CtaAhorroStrategy`: Estrategia para calcular intereses en cuentas de ahorro.
    - `CtaCorrienteStrategy`: Estrategia para calcular intereses en cuentas corrientes.
- **`com.SofBank.bank.infraestructure.logging`**: Contiene la clase relacionada con el patrón **Singleton**:
    - `TransactionLogger`: Clase responsable de registrar las transacciones realizadas en las cuentas.
- **`com.SofBank.bank.domains.Observer`** (si se implementa): Contendrá las clases necesarias para notificar cambios en las cuentas.

## Principios SOLID

El diseño del sistema respeta los principios SOLID:
1. **Responsabilidad única (SRP)**: Cada clase tiene una única responsabilidad.
2. **Abierto/Cerrado (OCP)**: Las clases están abiertas para la extensión pero cerradas para la modificación.
3. **Sustitución de Liskov (LSP)**: Las subclases pueden sustituir a sus superclases sin alterar el comportamiento esperado.
4. **Segregación de Interfaces (ISP)**: Las interfaces son específicas al cliente, evitando métodos innecesarios.
5. **Inversión de Dependencia (DIP)**: Las clases de alto nivel no dependen de las de bajo nivel, sino de abstracciones.

## Ejecución

Ejecutar la clase `Main` para probar las funcionalidades básicas del sistema. Ejemplo del flujo en `Main`:

```java
ICuentaBancaria cuentaAhorro = ctaAhorroFactory.crearCuenta("111111111", 1000, 0.087, TipoCuenta.AHORRO);
cuentaAhorro.depositar(700.0);
cuentaAhorro.retirar(200);
cuentaAhorro.depositar(300);
cuentaAhorro.mostrarEstadoCuenta();

ICuentaBancaria cuentaCorriente = ctaCorrienteFactory.crearCuenta("222222222", 500, 0.087, TipoCuenta.CORRIENTE);
cuentaCorriente.retirar(200);
cuentaCorriente.depositar(50);
cuentaCorriente.mostrarEstadoCuenta();
```

## Funcionalidades
Creación de cuentas bancarias: Usando el patrón Factory, se pueden crear diferentes tipos de cuentas bancarias.
Cálculo de intereses: Con el patrón Strategy, se calcula el interés según el tipo de cuenta.
Registro de transacciones: Gracias al patrón Singleton, todas las transacciones son registradas globalmente.
Extensibilidad: La estructura permite agregar nuevos tipos de cuentas y estrategias de cálculo fácilmente.

## Contribuciones
Las contribuciones son bienvenidas. Si encuentras algún error o tienes sugerencias de mejora, no dudes en abrir un issue o realizar un pull request.

## Autor
Julian Steven Huerfano.