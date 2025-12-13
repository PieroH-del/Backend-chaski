package com.chaski.Backend_chaski.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            InputStream serviceAccount = null;

            // Opción 1: Variable de entorno FIREBASE_CREDENTIALS (para Azure/producción)
            String firebaseCredentials = System.getenv("FIREBASE_CREDENTIALS");
            if (firebaseCredentials != null && !firebaseCredentials.isEmpty()) {
                serviceAccount = new ByteArrayInputStream(firebaseCredentials.getBytes(StandardCharsets.UTF_8));
                System.out.println("📌 Usando credenciales Firebase desde variable de entorno");
            }

            // Opción 2: Archivo en resources (para desarrollo local)
            if (serviceAccount == null) {
                serviceAccount = getClass().getClassLoader()
                        .getResourceAsStream("firebase-service-account.json");
                if (serviceAccount != null) {
                    System.out.println("📌 Usando credenciales Firebase desde archivo en resources");
                }
            }

            // Opción 3: Archivo en ruta del sistema (para desarrollo local alternativo)
            if (serviceAccount == null) {
                try {
                    serviceAccount = new FileInputStream("src/main/resources/firebase-service-account.json");
                    System.out.println("📌 Usando credenciales Firebase desde ruta del sistema");
                } catch (IOException ignored) {
                }
            }

            if (serviceAccount == null) {
                System.err.println("⚠️ No se encontraron credenciales de Firebase");
                System.err.println("   Para desarrollo local: coloca firebase-service-account.json en src/main/resources/");
                System.err.println("   Para Azure: configura la variable de entorno FIREBASE_CREDENTIALS");
                return;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            System.out.println("✅ Firebase Admin SDK inicializado correctamente");
        } catch (IOException e) {
            System.err.println("❌ Error al inicializar Firebase: " + e.getMessage());
        }
    }
}
