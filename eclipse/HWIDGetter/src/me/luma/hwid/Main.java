package me.luma.hwid;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import me.luma.hwid.utils.HWIDUtil;

public class Main extends JFrame {

	public static void main(String[] args) throws HeadlessException, NoSuchAlgorithmException, UnsupportedEncodingException {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(HWIDUtil.getHWID()), null); // Copy HWID to clipboard
		JOptionPane.showMessageDialog(null, "README.md must be read. HWID copied to clipboard!");
	}

}
