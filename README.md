# budgie-security
Budgie Security es un proyecto que permite administrar la autenticación y autorización de usuarios, roles y permisos para cualquier aplicación. Es un proyecto que esta basado en oAuth 2.0 como protocolo de autenticación, lo que le da robustez y permite a aplicaciones de terceros obtener acceso limitado a recursos HTTP.

Enfocado en la simplicidad del desarrollo y proporciona flujos de autorización específicos.    
- Web
- Mobile
- Escritorio
- API´s


![Flujo para solicitud de acceso](https://s3.amazonaws.com/billers-images/oauth_flow_1.png "Flujo oAuth")

## Roles
- Propietario del Recurso
    
    > Entidad capaz de otorgar acceso a un recurso.

- Servidor de Recursos

    > Servidor que aloja los recursos protegidos respondiendo a solicitudes con tokens de acceso.

- Servidor de autorización

    > Servidor que emite los tokens de acceso después de haber autenticado al propietario del recurso.

- Cliente

    > Aplicación que realiza peticiones a un recurso protegido.

## Autorización
Es la acción de otorgar privilegios y poder acceder a algunos recursos.
Proteger los recursos de un sistema permitiendo que solo sean usados por consumidores a los que se les ha concedido la autorización.

## Autenticación
Capacidad de demostrar que un usuario o una aplicación es quien realmente dicha persona o aplicación asegura ser.

# Flujo principal
![Flujo para solicitud de acceso](https://s3.amazonaws.com/billers-images/autorization_flow.png "Flujo Principal")

Descripción del flujo
1. La **aplicación cliente** solicita autorización para acceder a los recursos del propietario de recursos.
2. Si el **propietario de recursos** autoriza la solicitud, la aplicación cliente recibe la autorización.
3. La aplicación cliente solicita al **servidor de autorización los tokens de acceso**, en este momento la aplicación cliente deberá presentar su identidad y la autorización otorgada.
4. Si la identidad de la aplicación cliente es autenticada y autorizada, el **servidor de autorización generará y enviará token de acceso** a la aplicación cliente.
5. La aplicación cliente solicita el recurso al **servidor de recursos** y presenta el token de acceso para autenticarse.
6. Si el token de acceso es válido, el servidor de recursos proporciona.

# Documentación

SSO Spring oAuth [SSO](https://www.baeldung.com/sso-spring-security-oauth2)
EnableResourceServer vs EnableOAuth2Sso [Docuemtación](https://www.baeldung.com/spring-security-oauth2-enable-resource-server-vs-enable-oauth2-sso)
Modificar la página de aprovación [autoapproval=true](http://programandonet.com/questions/48800/spring-oauth2-pagina-personalizada-de-oauth-approval-en-oauth-authorize)
Partes de oAuth2 [AuthorizationServer](https://docs.spring.io/spring-security-oauth2-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-security-oauth2-authorization-server)