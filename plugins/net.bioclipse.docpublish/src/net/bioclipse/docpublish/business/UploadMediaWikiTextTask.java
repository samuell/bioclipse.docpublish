package net.bioclipse.docpublish.business;

import java.net.MalformedURLException;

import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class UploadMediaWikiTextTask extends Task {
	private String wikiUrl = null;
	private String mwUser = null;
	private String mwPwd = null;
	private String sourceFile = null;
	private String mwTitle = null;

	private MediaWikiBot mwBot = null;

	public void execute() throws BuildException {
		try {
			mwBot = new MediaWikiBot(wikiUrl);
		} catch (MalformedURLException e1) {
			System.err.println("ERROR: Could not initialize MediaWikiBot");
			e1.printStackTrace();
		}

		try {
			mwBot = new MediaWikiBot(wikiUrl);
			try {
				boolean isEditApi = mwBot.isEditApi();
				if (isEditApi) {
					System.out.println("Is Edit API!");
				} // TODO This check does not tell the truth
				mwBot.login(mwUser, mwPwd);
			} catch (Exception e) {
				System.err
						.println("Error logging in \n(Are you specifying the full path the folder where index.php lies? (don't forget any \"/w\"):\n"
								+ e.getMessage() + "\n-------------");
			}
		} catch (Exception e) {
			System.err.println("Error initializing MW Bot:\n" + e.getMessage()
					+ "\n-------------");
			e.printStackTrace();
		}

		SimpleArticle articleToEdit;
		try {
			articleToEdit = new SimpleArticle(mwBot.readContent(mwTitle));
			System.out.println("Is edit API: " + mwBot.isEditApi());
			System.out.println("Is logged in: " + mwBot.isLoggedIn());
			String wikiSource = MWUtils.readFileInWorkspace(sourceFile);
			articleToEdit.setText(wikiSource);
			mwBot.writeContent(articleToEdit);
		} catch (ActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
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

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public void setMwTitle(String mwTitle) {
		this.mwTitle = mwTitle;
	}

}
