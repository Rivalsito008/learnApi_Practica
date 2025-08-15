package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
public class CloudinaryService {
    //Definir el tamaño de las imagenes en MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    //Definir las extensiones permitidas
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};
    //Atributo cloudinary
    private final Cloudinary cloudinary;
    //Constructor para inyeccion de dependencia de Cloudinary
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file)throws IOException {
        validateImage(file);
    }

    private void validateImage(MultipartFile file) {
        //Verificar si el archivo esta vacio
        if(file.isEmpty()){
            throw new IllegalArgumentException("El archivo esta vacío");
        }
        //Verificar si el tamaño excede el limite permitido
        if(file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("El archivo sobrepasa los 5MB");
        }
        //Verificar el nombre del archivo
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null){
            throw new IllegalArgumentException("Nombre de archivo invalido");
        }
        //Extraer y validar la extension del archivo
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)){
            throw new IllegalArgumentException("Solo se permiten archivo JPG, JPEG, PNG");
        }

        //Verificamos que el tipo NIME sea una imagen
        if(!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("El archivo debe de ser una imagen valida");
        }
    }
}
