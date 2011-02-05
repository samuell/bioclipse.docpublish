package net.bioclipse.docpublish.business;

import java.io.File;
import java.net.MalformedURLException;

import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.mediawiki.actions.editing.FileUpload;
import net.sourceforge.jwbf.mediawiki.actions.util.VersionException;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import net.sourceforge.jwbf.mediawiki.contentRep.SimpleFile;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class UploadMediaWikiFileTask extends Task {
	private String wikiUrl = null;
	private String mwUser = null;
	private String mwPwd = null;
	private String filePath = null;
	
	private MediaWikiBot mwBot = null;

	public void execute() throws BuildException {
		File testFile = new File(filePath);

		// TODO: The following code block might not be needed? 
		if ( testFile.exists() ) {
			System.out.println("The file or directory exists!");
			if ( !testFile.isFile() )  {
				System.out.println("The file is not a normal file");
			} else {
				System.out.println("The file is a normal file");
				// TODO: Should the following method code be placed here?
			}
		} else {
			System.out.println("File does not exist!");
		}

		try {
			mwBot = new MediaWikiBot(wikiUrl);
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		}
		try {
			mwBot.login(mwUser, mwPwd);
		} catch (ActionException ae) {
			ae.printStackTrace();
		}

		// TODO: Remove?
		// String fullPath = null;
		// fullPath = filePathToFullPath(filePath);
		// SimpleFile file = new SimpleFile(fullPath);
		SimpleFile file = new SimpleFile(filePath);
		FileUpload actionFileUpload;
		try {
			actionFileUpload = new FileUpload(file, mwBot);
			System.out.println("MW Bot version: " + mwBot.getVersion());
			mwBot.performAction(actionFileUpload);
		} catch (VersionException e1) {
			e1.printStackTrace();
		} catch (ActionException e1) {
			e1.printStackTrace();
		} catch (ProcessException e) {
			e.printStackTrace();
		}
	}

	public void setWikiUrl(String wikiUrl) {
		this.wikiUrl = wikiUrl;
	}

	public void setMwUser(String mwUser) {
		this.mwUser = mwUser;
	}

	public void setMwPwd(String mwPwd) {
		this.mwPwd = mwPwd;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
