package com.notesforme.Bcrypt;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class serviceBcryptImpl implements serviceBcrypt{
	
	@Override
	public String contrasenaEncrypt(String contrasena) {
		return BCrypt.hashpw(contrasena, BCrypt.gensalt());
	}

	@Override
	public boolean verifyPasswd(String contrasenaOriginal, String contrasenaEncrypt) {
		return BCrypt.checkpw(contrasenaOriginal, contrasenaEncrypt);
	}
}
