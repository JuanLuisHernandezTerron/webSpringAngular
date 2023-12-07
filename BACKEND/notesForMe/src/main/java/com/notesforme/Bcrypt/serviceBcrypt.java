package com.notesforme.Bcrypt;

public interface serviceBcrypt {

	/**
	 * Con este metodo estaremos encriptando la contraseña
	 * @param contrasena 
	 * @return devuelve la contraseña encriptada
	 */
	public String contrasenaEncrypt(String contrasena);
	
	/**
	 * Verifica que la contraseña proporcionada en el login es correcta con la de la BBDD
	 * @param contrasenaOriginal
	 * @param contrasenaEncrypt
	 * @return devuelve true o false dependiendo si las contraseñas coinciden.
	 */
	public boolean verifyPasswd(String contrasenaOriginal,String contrasenaEncrypt);
}
