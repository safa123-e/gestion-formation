package com.demo.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Component
public class SftpTools {
	@Value("${ftp.password}")
	private String password;
	@Value("${ftp.user}")
	private String user;
	@Value("${ftp.host}")
	private String host;
	@Value("${ftp.port}")
	private int port;

	private SftpTools() {
		super();
	}

	private static final Logger logger = LoggerFactory.getLogger(SftpTools.class);

	public ChannelSftp connectSftp() {
		Session session = null;
		ChannelSftp sftpChannel = null;

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			// Timeout plus généreux pour éviter les erreurs "socket is not established"
			session.connect(500); // 10 secondes max
			logger.debug(" Session SFTP connectée à {}:{}", host, port);

			// Ouverture du canal SFTP
			sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect(500); // 5 secondes max
			logger.info("Canal SFTP ouvert avec succès vers {}:{}", host, port);

			return sftpChannel;

		} catch (JSchException e) {
			logger.error(" Erreur lors de la connexion SFTP : {}", e.getMessage());

			// Si échec, on ferme proprement la session
			if (session != null && session.isConnected()) {
				session.disconnect();
				logger.debug("Session SFTP fermée suite à une erreur.");
			}

			return null; // renvoyer null si échec
		}
	}

	public static void disconnectSftp(ChannelSftp sftpChannel) throws JSchException {
		if (sftpChannel != null) {
			Session session = sftpChannel.getSession();
			sftpChannel.disconnect();
			if (session != null)
				session.disconnect();
		}
	}

	public byte[] getImageFromFtp(String remotePath, ChannelSftp sftpChannel) throws JSchException {

		try {

			if (sftpChannel == null) {
				logger.warn("Connexion FTP non établie. Lecture annulée.");
				return new byte[0];
			}

			sftpChannel.getSession();

			try (InputStream stream = sftpChannel.get(remotePath)) {
				logger.info("Image récupérée depuis le FTP : {}", remotePath);
				return stream.readAllBytes();
			}

		} catch (SftpException | IOException e) {
			logger.error("Erreur lors de la récupération de l'image : {}", e.getMessage());
			return new byte[0];

		}
	}

}
