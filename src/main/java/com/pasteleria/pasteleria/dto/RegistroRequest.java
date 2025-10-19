package com.pasteleria.pasteleria.dto;

// Si usas Lombok, puedes añadir @Getter y @Setter
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
public class RegistroRequest {
    
    private String username;
    private String password;
    private String email;
    private String rol; // Para registrar el rol (Cliente o Administrador)

    // Constructor vacío (necesario para Spring)
    public RegistroRequest() {}

    // Constructor con todos los campos (opcional, pero útil)
    public RegistroRequest(String username, String password, String email, String rol) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
    }

    // Métodos Getters y Setters
    // Si NO usas Lombok, debes añadirlos manualmente:
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
