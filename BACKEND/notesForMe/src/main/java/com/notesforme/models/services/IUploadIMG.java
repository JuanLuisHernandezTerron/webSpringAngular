package com.notesforme.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadIMG {
	public Path getPath(String nombreImagen);
	
	public boolean eliminar(String nombreImagen);
	
	public Resource cargarIMG(String nombreImagen) throws MalformedURLException;
	
	public String copiar(MultipartFile archivo) throws IOException;
}
