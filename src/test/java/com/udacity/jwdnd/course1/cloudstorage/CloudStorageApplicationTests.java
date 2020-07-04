package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @Autowired
    UsersService usersService;
    @LocalServerPort
    private int port;
    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    private String getRandomString() {
        return UUID.randomUUID().toString();
    }

    private String getRandomUrl() {
        return String.format("https://%s/.com", getRandomString());
    }

    private String getRandomUserName() {
        return getRandomString() + "@" + "gmail.com";
    }

    private String getRandomPassword() {
        return getRandomString();
    }

    public String getLoginUrl() {
        return "http://localhost:" + this.port + "/login";
    }

    public String getHomeUrl() {
        return "http://localhost:" + this.port + "/home";
    }

    public String getSignupUrl() {
        return "http://localhost:" + this.port + "/signup";
    }

    public void registerUser(String username, String firstName, String lastName, String password) {
        WebElement fName = driver.findElement(By.id("inputFirstName"));
        fName.sendKeys(firstName);
        WebElement uName = driver.findElement(By.id("inputUsername"));
        uName.sendKeys(username);
        WebElement pWord = driver.findElement(By.id("inputPassword"));
        pWord.sendKeys(password);
        WebElement lName = driver.findElement(By.id("inputLastName"));
        lName.sendKeys(lastName);
        WebElement signUp = driver.findElement(By.xpath("//button[text()='Sign Up']"));
        signUp.click();
    }

    public void login(String username, String password) {
        WebElement uName = driver.findElement(By.id("inputUsername"));
        uName.sendKeys(username);
        WebElement pWord = driver.findElement(By.id("inputPassword"));
        pWord.sendKeys(password);
        WebElement loginButtton = driver.findElement(By.xpath("//button[text()='Login']"));
        loginButtton.click();
    }

    private void logout() {
        WebElement logoutButton = driver.findElement(By.xpath("//button[text()='Logout']"));
        logoutButton.click();
    }

    private void createNote(String title, String description) {
        WebDriverWait wait = getWait();
        WebElement addNotesButton = driver.findElement(By.id("add-note-button"));
        wait.until(ExpectedConditions.elementToBeClickable(addNotesButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title"))).sendKeys(title);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description"))).sendKeys(description);
        WebElement saveNote = driver.findElement(By.id("note-form"));
        saveNote.submit();
    }

    private void createCredential(String username, String password, String url) {
        ////*[@id="credentialTable"]/tbody/tr[1]/th
        WebDriverWait wait = getWait();
        WebElement addCredentialButton = driver.findElement(By.id("add-credential-button"));
        wait.until(ExpectedConditions.elementToBeClickable(addCredentialButton)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username"))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password"))).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url"))).sendKeys(url);
        WebElement saveCredentialForm = driver.findElement(By.id("credential-form"));
        saveCredentialForm.submit();
    }

    private void editCredential(String username, String password, String url) {
        WebDriverWait wait = getWait();
        ////*[@id="credentialTable"]/tbody/tr[1]/td[1]/button
        WebElement credsTable = driver.findElement(By.id("credentialTable"));
        WebElement editCredentialButton = credsTable.findElement(By.xpath(".//tbody/tr[1]/td[1]/button"));
        wait.until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username"))).sendKeys(username);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password"))).sendKeys(password);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url"))).sendKeys(url);
        WebElement saveCredentialForm = driver.findElement(By.id("credential-form"));
        saveCredentialForm.submit();
    }

    private void deleteCredential() {
        WebDriverWait wait = getWait();
        WebElement credsTable = driver.findElement(By.id("credentialTable"));
        ////*[@id="credentialTable"]/tbody/tr[1]/td[1]/a
        WebElement deleteCredentialButton = credsTable.findElement(By.xpath(".//tbody/tr[1]/td[1]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(deleteCredentialButton)).click();
    }

    private void uploadFile(String filePath) {
        WebDriverWait wait = getWait();
        WebElement fileInput = driver.findElement(By.id("fileUpload"));
        wait.until(ExpectedConditions.visibilityOf(fileInput));
        fileInput.sendKeys(filePath);
        WebElement fileForm = driver.findElement(By.id("file-form"));
        fileForm.submit();
    }

    private void deleteFile() {
        WebDriverWait wait = getWait();
        WebElement fileTable = driver.findElement(By.id("fileTable"));
        WebElement deleteFileButton = fileTable.findElement(By.xpath(".//tbody/tr/td/a[2]"));
        wait.until(ExpectedConditions.elementToBeClickable(deleteFileButton));
        deleteFileButton.click();
    }


    public void goToNotesTab() {
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();
    }

    public void goToFilesTab() {
        WebElement noteTab = driver.findElement(By.id("nav-files-tab"));
        noteTab.click();
    }

    /**
     * Navigates to the Credentials tab from home
     */
    public void goToCredentialsTab() {
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());
        WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
        credTab.click();
    }

    public void registerAndLogin() {
        String username = getRandomUserName();
        String password = getRandomPassword();
        String firstName = getRandomString();
        String lastName = getRandomString();
        driver.get(getSignupUrl());
        registerUser(username, firstName, lastName, password);
        String loginUrl = getLoginUrl();
        driver.get(loginUrl);
        login(username, password);
    }


    public String serverUrl() {
        return "http://localhost:" + this.port;
    }


    @Test
    public void getLoginPage() {
        driver.get(getLoginUrl());
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void getRegistrationPage() {
        driver.get(getSignupUrl());
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    public void testUserRegistration() {
        String username = getRandomUserName();
        String password = getRandomPassword();
        String firstName = getRandomString();
        String lastName = getRandomString();
        driver.get(getSignupUrl());
        registerUser(username, firstName, lastName, password);
        String loginUrl = getLoginUrl();
        //user is redirected to login page after signup
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals(currentUrl, loginUrl);
    }

    @Test
    public void testLogin() {

        //register user
        String username = getRandomUserName();
        String password = getRandomPassword();
        String firstName = getRandomString();
        String lastName = getRandomString();
        driver.get(getSignupUrl());
        String homeUrl = getHomeUrl();
        registerUser(username, firstName, lastName, password);
        String loginUrl = getLoginUrl();

        //user cannot access home url without login in
        driver.get(homeUrl);
        String destination = driver.getCurrentUrl();
        Assertions.assertEquals(destination, loginUrl);
        Assertions.assertNotEquals(homeUrl, destination);

        //when user logs in, they can access home url
        driver.get(homeUrl);
        login(username, password);
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals(homeUrl, currentUrl);

        driver.navigate().to(homeUrl);
        logout();
        driver.navigate().to(homeUrl);
        destination = driver.getCurrentUrl();
        Assertions.assertNotEquals(homeUrl, destination);
    }

    WebDriverWait getWait() {
        return new WebDriverWait(driver, 100);
    }

    @Test
    public void testNoteAdd() {

        //when user creates an account and logs in
        registerAndLogin();


        driver.get(getHomeUrl());
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());

        goToNotesTab();
        //Check the number of notes before a note is created.
        WebElement notesTable = driver.findElement(By.id("notesTable"));
        List<WebElement> tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertEquals(0, tableRows.size());
        String newNoteTitle = "New Note Title 1";
        String newNoteDescription = "New Note Description";
        createNote(newNoteTitle, newNoteDescription);

        driver.navigate().to(getHomeUrl());

        goToNotesTab();

        WebDriverWait wait = getWait();
        notesTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notesTable")));
        tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        WebElement noteTitleCell = notesTable.findElement(By.xpath(".//tbody/tr[1]/th"));
        WebElement noteDescriptionCell = notesTable.findElement(By.xpath(".//tbody/tr[1]/td[2]"));

        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(noteTitleCell.getText(), newNoteTitle);
        Assertions.assertEquals(noteDescriptionCell.getText(), newNoteDescription);
    }

    @Test
    public void testDeleteNote() {

        registerAndLogin();

        driver.get(getHomeUrl());

        goToNotesTab();
        String noteTitle = "New note title";
        String noteDescription = "Sample note description";
        createNote(noteTitle, noteDescription);

        driver.navigate().to(getHomeUrl());
        goToNotesTab();
        WebDriverWait wait = getWait();

        WebElement notesTable = driver.findElement(By.id("notesTable"));
        List<WebElement> tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        notesTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notesTable")));
        tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        WebElement newNoteRow = notesTable.findElement(By.xpath(".//tbody/tr[1]"));
        WebElement noteTitleCell = notesTable.findElement(By.xpath(".//tbody/tr[1]/th"));
        WebElement noteDescriptionCell = notesTable.findElement(By.xpath(".//tbody/tr[1]/td[2]"));

        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(noteTitleCell.getText(), noteTitle);
        Assertions.assertEquals(noteDescriptionCell.getText(), noteDescription);

        //when note is deleted
        WebElement deleteButton = newNoteRow.findElement(By.xpath(".//td/a"));
        deleteButton.click();

        driver.navigate().to(getHomeUrl());
        goToNotesTab();

        notesTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notesTable")));
        List<WebElement> tableRowsNow = notesTable.findElements(By.xpath(".//tbody/tr"));
        // Check that the note is successfully deleted.
        Assertions.assertTrue(tableRows.size() > tableRowsNow.size());
        Assertions.assertEquals(0, tableRowsNow.size());

    }

    @Test
    public void testEditNote() {
        registerAndLogin();

        driver.get(getHomeUrl());

        goToNotesTab();
        String noteTitle = "New note title";
        String noteDescription = "Sample note description";
        createNote(noteTitle, noteDescription);

        driver.navigate().to(getHomeUrl());
        goToNotesTab();
        WebDriverWait wait = getWait();

        WebElement notesTable = driver.findElement(By.id("notesTable"));
        List<WebElement> tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        WebElement newNoteRow = notesTable.findElement(By.xpath(".//tbody/tr[1]"));
        WebElement noteTitleCell = notesTable.findElement(By.xpath(".//tbody/tr[1]/th"));
        wait.until(ExpectedConditions.visibilityOf(noteTitleCell));
        WebElement noteDescriptionCell = notesTable.findElement(By.xpath(".//tbody/tr[1]/td[2]"));
        wait.until(ExpectedConditions.visibilityOf(noteDescriptionCell));

        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(noteTitleCell.getText(), noteTitle);
        Assertions.assertEquals(noteDescriptionCell.getText(), noteDescription);

        //test that values change when note is edited
        WebElement editNoteButton = newNoteRow.findElement(By.xpath(".//td/button"));

        noteTitle = "Edited Note Title";
        noteDescription = "Edited Note Description";
        wait.until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title"))).sendKeys("Edited Note Title");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description"))).sendKeys("Edited Note Description");
        WebElement saveNote = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-form")));
        saveNote.submit();

        //then note values are edited
        driver.navigate().to(getHomeUrl());
        goToNotesTab();

        notesTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notesTable")));
        tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        newNoteRow = notesTable.findElement(By.xpath(".//tbody/tr[1]"));
        noteTitleCell = newNoteRow.findElement(By.xpath(".//th"));
        noteDescriptionCell = newNoteRow.findElement(By.xpath(".//td[2]"));

        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(noteTitleCell.getText(), noteTitle);
        Assertions.assertEquals(noteDescriptionCell.getText(), noteDescription);
    }

    @Test
    public void testCredentialsCrud() {
        registerAndLogin();
        driver.get(getHomeUrl());
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());
        goToCredentialsTab();

        WebDriverWait wait = getWait();

        WebElement credsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credsRow = credsTable.findElements(By.xpath(".//tbody/tr"));

        //when no data is available
        Assertions.assertEquals(credsRow.size(), 0);

        //when a new credential is created
        String username = getRandomUserName();
        String password = getRandomPassword();
        String url = getRandomUrl();
        createCredential(username, password, url);

        driver.navigate().to(getHomeUrl());
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());
        goToCredentialsTab();


        credsTable = driver.findElement(By.id("credentialTable"));
        credsRow = credsTable.findElements(By.xpath(".//tbody/tr"));

        Assertions.assertTrue(credsRow.size() > 0);

        //wait for elements to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//tbody/tr[1]/th")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//tbody/tr[1]/td[3]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//tbody/tr[1]/td[2]")));

        WebElement urlCell = credsTable.findElement(By.xpath(".//tbody/tr[1]/th"));
        WebElement passwordCell = credsTable.findElement(By.xpath(".//tbody/tr[1]/td[3]"));
        WebElement usernameCell = credsTable.findElement(By.xpath(".//tbody/tr[1]/td[2]"));

        Assertions.assertEquals(urlCell.getText(), url);
        Assertions.assertNotEquals(usernameCell.getText(), username);
        String encryptedPassword = passwordCell.getText();
        //password is encrypted before displaying on the interface
        Assertions.assertNotEquals(encryptedPassword, null);
        Assertions.assertNotEquals(encryptedPassword, password);


        //test that update works as expected
        url = getRandomUrl();
        password = getRandomPassword();
        username = getRandomUserName();

        driver.navigate().to(getHomeUrl());
        goToCredentialsTab();
        editCredential(username, password, url);

        driver.get(getHomeUrl());
        goToCredentialsTab();

//        wait for visibility
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//tbody/tr[1]/th")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//tbody/tr[1]/td[3]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//tbody/tr[1]/td[2]")));

        //the values are updated successfully
        credsTable = driver.findElement(By.id("credentialTable"));
        credsRow = credsTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertTrue(credsRow.size() > 0);


        passwordCell = credsTable.findElement(By.xpath(".//tbody/tr[1]/td[3]"));
        urlCell = credsTable.findElement(By.xpath(".//tbody/tr[1]/th"));
        usernameCell = credsTable.findElement(By.xpath(".//tbody/tr[1]/td[2]"));
        String secondEncryptedPassword = passwordCell.getText();

        Assertions.assertEquals(urlCell.getText(), url);
        Assertions.assertNotEquals(usernameCell.getText(), username);
        //password is encrypted before displaying on the interface
        Assertions.assertNotEquals(secondEncryptedPassword, null);
        Assertions.assertNotEquals(secondEncryptedPassword, password);
        Assertions.assertNotEquals(secondEncryptedPassword, encryptedPassword);

        //test that credentials are deleted successfully
        deleteCredential();

        driver.get(getHomeUrl());
        goToCredentialsTab();
        credsTable = driver.findElement(By.id("credentialTable"));
        credsRow = credsTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertEquals(credsRow.size(), 0);
    }

    @Test
    public void testFilesCrud() {
        WebDriverWait wait = getWait();
        registerAndLogin();
        driver.get(getHomeUrl());
        goToFilesTab();

        //test create file

        //no file is present so none is displayed to the client
        WebElement fileTable = driver.findElement(By.id("fileTable"));
        List<WebElement> fileRow = fileTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertEquals(fileRow.size(), 0);

        //when a new file is upload
        String baseDir = System.getProperty("user.dir");
        String filePath = String.format("%s/src/test/java/com/udacity/jwdnd/course1/cloudstorage/newFile", baseDir);
        uploadFile(filePath);

        driver.get(getHomeUrl());
        goToFilesTab();

        //the file is displayed to the client
        fileTable = driver.findElement(By.id("fileTable"));
        fileRow = fileTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertTrue(fileRow.size() > 0);
        WebElement filenameCell = fileTable.findElement(By.xpath(".//tbody/tr[1]/th"));
        wait.until(ExpectedConditions.visibilityOf(filenameCell));
        Assertions.assertEquals(filenameCell.getText(), "fileUpload");

        //test file delete
        driver.get(getHomeUrl());
        goToFilesTab();
        deleteFile();

        driver.get(getHomeUrl());
        goToFilesTab();

        fileTable = driver.findElement(By.id("fileTable"));
        fileRow = fileTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertEquals(fileRow.size(), 0);

    }


}
