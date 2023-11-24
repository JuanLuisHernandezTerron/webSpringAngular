package com.notesforme.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class IUploadImgImpl implements IUploadIMG {
	
	private final static String NOMBRE_DIRECTORIO = "uploads";

	@Override
	public Path getPath(String nombreImagen) {
		return Paths.get(NOMBRE_DIRECTORIO).resolve(nombreImagen).toAbsolutePath();
	}

	@Override
	public boolean eliminar(String fotoAnterior) {
		if (fotoAnterior !=null && fotoAnterior.length() > 0) {
			Path rutaImgAntigua = Paths.get("uploads").resolve(fotoAnterior).toAbsolutePath();
			File archivoAnterioImg = rutaImgAntigua.toFile();
			if (archivoAnterioImg.exists() && archivoAnterioImg.canRead()) {
				archivoAnterioImg.delete();
				return true;
			}
		}
		return false;
	}

	@Override
	public Resource cargarIMG(String nombreImagen) throws MalformedURLException {
		Path rutaImgAntigua = getPath(nombreImagen);
		Resource resource = null;		
		resource = new UrlResource(rutaImgAntigua.toUri());
		if (!resource.exists() && !resource.isReadable()) {
			rutaImgAntigua =  Paths.get("/notesForMe/src/main/resources/static/img").resolve("Profile-PNG-Clipart.png").toAbsolutePath();
			resource = new UrlResource(rutaImgAntigua.toUri());
		}
		return resource;
	}

	/**
	 * Copiamos el archivo en la ruta que hemmos instanciado anteriormente
	 * nombreArchivo - Guardamos en esta variable el nombre del archivo
	 * rutaIMG - Guardamos en la carpeta uploads y con el toAbsolutePath,donde tendrá la ruta completa.
	 * UUID.randomUUID() - Lo utilizamos para que la imagen tenga un id unico, y al final utilizamos el replace para que borre todos los espacios en blanco.
	 */
	
	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		String nombreArchivo = UUID.randomUUID().toString().concat("_").concat(archivo.getOriginalFilename()).replace(" ", "");
		Path rutaIMG = getPath(nombreArchivo);
		Files.copy(archivo.getInputStream(), rutaIMG);
		return nombreArchivo;
	}
	
	

}
