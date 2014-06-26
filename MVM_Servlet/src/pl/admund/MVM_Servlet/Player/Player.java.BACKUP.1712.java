package pl.admund.MVM_Servlet.Player;

import java.util.Properties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.admund.MVM_Servlet.DBConnect.DBUtils;
import pl.admund.MVM_Servlet.XML.XMLUtils;
import pl.admund.Security.HTTPSecurity;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Player
{
	private int mId;
	private String mNick;
	private String mToken;
	private String mMail;
	private int mTeamId;
	private int status;

	public static Player createPlayer(int teamId, String _nickname, String _token, int _teamId, String _mail)
	{
		//System.out.println("createPlayer " + _name);
		Player tmpPlayer = new Player();
		
		tmpPlayer.setId(teamId);
		tmpPlayer.setNick(_nickname);
		tmpPlayer.setToken(_token);
		tmpPlayer.setMail(_mail);
		tmpPlayer.setmTeamId(_teamId);
		tmpPlayer.setStatus(0);
		
		return tmpPlayer;
	}
	
	public static Player createRandomPlayer(int _teamId)
	{
		//System.out.print("creatRandomVolleyballer\n");
		Player tmpPlayer = new Player();
		
		tmpPlayer.mId = _teamId;
		tmpPlayer.mNick = "RAND_NICK";
		tmpPlayer.mToken = "token";
		tmpPlayer.setMail("random@mail.com");
		tmpPlayer.setmTeamId(_teamId);
		tmpPlayer.setStatus(0);
		
		return tmpPlayer;
	}
	
	public boolean sendActivitationMail() throws MessagingException
	{
		String host = "smtp.gmail.com";
	    String from = "gardian.adrian@gmail.com";
<<<<<<< HEAD
	    String pass = "password";
=======
	    String pass = "password_tutu";
>>>>>>> 252c6e3309938cc5e491e13fc198e69de81db1ca
	    Properties props = System.getProperties();
	    props.put("mail.smtp.starttls.enable", "true"); // added this line
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.user", from);
	    props.put("mail.smtp.password", pass);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");

	    Session session = Session.getDefaultInstance(props, null);
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(from));
	    
	    InternetAddress[] toAddress = new InternetAddress[1];
	    toAddress[0] = new InternetAddress(getMail());

	    for( int i=0; i < toAddress.length; i++) 
	    {
	        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	    }
	    
	    String msg = "Witaj " + getNick();
	    msg += "\n\nTwoje konto jest NIE aktywne, musisz kliknac w ponizszy link aby aktywowac konto:";
	    msg += "\n\nhttp://192.168.1.101:8383/AVMServlet/WelcomeServlet?q=";
	    msg += createActivitationLink();
	    msg += "\n\ni zapraszamy do gry!!!";
	    msg += "\n\nPozdrawiam\nAdmund & Company";
	    
	    message.setSubject("AVM_VolleyballManager link aktywacyjny");
	    message.setText(msg);
	    Transport transport = session.getTransport("smtp");
	    transport.connect(host, from, pass);
	    transport.sendMessage(message, message.getAllRecipients());
	    transport.close();
	    
		return true;
	}
	
	public boolean addToXmlDoc(Document doc, Element rootElement)
	{
		Element rootPlayer = doc.createElement("player");
		rootElement.appendChild(rootPlayer);
		
		XMLUtils.addToXMLDoc(doc, rootPlayer, "id", "" + mId);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "nick", "" + mNick);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "token", "" + mToken);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "mail", "" + mMail);
		
		return true;
	}
	
	public String createActivitationLink()
	{
		String tmp = "cmd=activ&pid=" + getId() + "&pass=" + getToken() + "&hash=terefere";
		return HTTPSecurity.encrypt(tmp);
	}
	
	public String getToken() {
		return mToken;
	}
	public void setToken(String mToken) {
		this.mToken = mToken;
	}
	public String getNick() {
		return mNick;
	}
	public void setNick(String mNick) {
		this.mNick = mNick;
	}
	public int getId() {
		return mId;
	}
	public void setId(int mId) {
		this.mId = mId;
	}
	public int getTeamId() {
		return mTeamId;
	}
	public void setmTeamId(int mTeamId) {
		this.mTeamId = mTeamId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMail() {
		return mMail;
	}
	public void setMail(String mMail) {
		this.mMail = mMail;
	}
}
