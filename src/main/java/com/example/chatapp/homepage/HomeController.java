package com.example.chatapp.homepage;
import com.example.chatapp.HelloApplication;
import com.example.chatapp.TranslatorClient;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import javafx.scene.control.Hyperlink;

public class HomeController implements Initializable {
    @FXML
    private Label GemLabel;

    @FXML
    private AnchorPane Root;

    @FXML
    private Tab aboutTab;

    @FXML
    private Label activitesLabel;

    @FXML
    private MenuItem arabic;

    @FXML
    private Button bookingBtn;

    @FXML
    private ImageView bookingImg1;

    @FXML
    private ImageView bookingImg2;

    @FXML
    private Tab bookingTab;

    @FXML
    private ImageView buttonImage;

    @FXML
    private Button chatbotButton;

    @FXML
    private AnchorPane chatbotPane;

    @FXML
    private Tab chatroomTab;

    @FXML
    private Button contactBtn;

    @FXML
    private Label contactLabel;

    @FXML
    private Tab contactTab;

    @FXML
    private Pagination cover;

    @FXML
    private MenuItem english;

    @FXML
    private Pagination gem;

    @FXML
    private Tab homeTab;

    @FXML
    private Label hoursLabel;

    @FXML
    private Label introLabel;

    @FXML
    private Pagination kids;

    @FXML
    private Label kidsLabel;

    @FXML
    private SplitMenuButton languageSelcetor;

    @FXML
    private Hyperlink link1;

    @FXML
    private Label newsLabel;

    @FXML
    private Hyperlink link10;

    @FXML
    private Hyperlink link2;

    @FXML
    private Hyperlink link3;

    @FXML
    private Hyperlink link4;

    @FXML
    private Hyperlink link5;

    @FXML
    private Hyperlink link6;

    @FXML
    private Hyperlink link7;

    @FXML
    private Hyperlink link8;

    @FXML
    private Hyperlink link9;


    @FXML
    private Button newsBtn;

    @FXML
    private Tab newsTab;

    @FXML
    private Label pyramidLabel;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label timeLabel;

    @FXML
    private Pagination tour;

    @FXML
    private Label welcomeLabel;
    int coverCurrentIndex = 0;

    Vector<Pagination> pags = new Vector<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyFadeTransition(Root, 1500);

        imageLoader();
        pagsTransitions();
        bookingImg1.setOnMouseClicked(mouseEvent -> openWebpage("https://visit-gem.com/en/tours"));
        bookingImg2.setOnMouseClicked(mouseEvent -> openWebpage("https://visit-gem.com/en/children"));
        contactBtn.setOnAction(e -> switchToTab("aboutTab"));
        bookingBtn.setOnAction(e -> switchToTab("bookingTab"));
        newsBtn.setOnAction(e -> switchToTab("newsTab"));
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                handleTabSelection(newTab);
            }
        });
        setArabic();

        chatbotButton.setOnAction(this::setChatbotButtonAction);
        Platform.runLater(() -> {
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setOnCloseRequest(this::handleCloseRequest);
        });
        chatbotButton.setOnAction(this::setChatbotButtonAction);

    }

    private void handleCloseRequest(WindowEvent e) {
        try {
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("close_app.fxml"));
            Parent homePageRoot = loader.load();
            Stage s = (Stage) tabPane.getScene().getWindow();

            s.close();

            Stage stage = new Stage();
            Scene scene = new Scene(homePageRoot, screenWidth, screenHeight, Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("error in signup button");
        }
    }

    private void imageLoader() {
        try {
            // Create ImageViews for cover pages and other images
            ImageView[] covers = {
                    new ImageView(new Image(Objects.requireNonNull(HelloApplication.class.getResource("images/coverPic 1.jpg")).toExternalForm())),
                    new ImageView(new Image(Objects.requireNonNull(HelloApplication.class.getResource("images/CoverPic 2.jpg")).toExternalForm())),
                    new ImageView(new Image(Objects.requireNonNull(HelloApplication.class.getResource("images/CoverPic 3.jpg")).toExternalForm())),
                    new ImageView(new Image(Objects.requireNonNull(HelloApplication.class.getResource("images/CoverPic 4.jpg")).toExternalForm()))
            };
            bookingTab.setStyle("");

            homeTab.setStyle("");
            newsTab.setStyle("");

            String[] imagePaths = {
                    "images/tour-1.png", "images/tour-2.png", "images/tour-3.png",
                    "images/gem-1.png", "images/gem-2.png", "images/gem-3.png", "images/gem-4.png",
                    "images/kids-1.png", "images/kids-2.png", "images/kids-3.png"
            };

            Vector<ImageView> images = new Vector<>();
            for (String path : imagePaths) {
                ImageView imageView = new ImageView(new Image(Objects.requireNonNull(HelloApplication.class.getResource(path)).toExternalForm()));
                images.add(imageView);
            }

            // Set up images with properties
            setupImagesWithRoundedCorners(images);

            // Set up cover images with dimensions
            for (ImageView cover : covers) {
                cover.setFitWidth(1364);
                cover.setFitHeight(384);
            }

            // Create pages for the cover
            cover.setPageFactory(index -> index >= 0 && index < covers.length ? covers[index % covers.length] : new ImageView());
            cover.setPageCount(4);

            // Create a timeline for auto-sliding the cover pages
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                coverCurrentIndex = (coverCurrentIndex + 1) % cover.getPageCount();
                cover.setCurrentPageIndex(coverCurrentIndex);
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            // Set page factories for tours, gems, and kids
            setPageFactory(tour, images, 0, 2);
            setPageFactory(gem, images, 3, 6);
            setPageFactory(kids, images, 7, 9);

        } catch (NullPointerException e) {
            System.out.println("Error loading images: " + e.getMessage());
        }


    }

    private void setupImagesWithRoundedCorners(Vector<ImageView> images) {
        for (ImageView image : images) {
            Rectangle clip = new Rectangle(220, 220);
            clip.setArcWidth(30);
            clip.setArcHeight(30);
            image.setClip(clip);
            image.setFitWidth(220);
            image.setFitHeight(220);
        }
    }

    public void setPageFactory(Pagination pagination, Vector<ImageView> imageViews, int startIdx, int endIdx) {
        int pageCount = endIdx - startIdx + 1;

        // Set up page factory dynamically
        pagination.setPageFactory(index -> {
            if (index >= 0 && index < pageCount) {
                return imageViews.get(startIdx + index);
            }
            return new ImageView(); // Return an empty image view for other pages
        });
        pagination.setPageCount(pageCount);

        // Create a timeline for auto-sliding the pages
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            pagination.setCurrentPageIndex((currentPageIndex + 1) % pageCount);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void openLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            System.out.println("Error in Open Link");
        }
    }

    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            System.out.println("Error in Open Link");
        }
    }

    private void switchToTab(String tabId) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getId().equals(tabId)) {
                tabPane.getSelectionModel().select(tab);
                break;
            }
        }
    }

    private void pagsTransitions() {
        pags.add(gem);
        pags.add(tour);
        pags.add(kids);

        pags.forEach(pagination -> {
            pagination.getStyleClass().add("tour-pag");
            pagination.setPrefWidth(250);
            pagination.setPrefHeight(250);
            pagination.setOnMouseEntered(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(200), pagination);
                st.setToX(1.1);
                st.setToY(1.1);
                st.play();
            });

            // Reset scale when hover ends
            pagination.setOnMouseExited(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(200), pagination);
                st.setToX(1);
                st.setToY(1);
                st.play();
            });

        });
    }

    private void applyFadeTransition(Node node, int durationInMillis) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(durationInMillis), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    private void loadTabContent(String tabId, String fxmlFile) throws IOException {
        // Find the tab by ID from the tabPane's tabs list
        Tab targetTab = tabPane.getTabs().stream()
                .filter(tab -> tabId.equals(tab.getId()))
                .findFirst()
                .orElse(null);

        if (targetTab != null) {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Node content = loader.load(); // Load the FXML content

            // Set the loaded content as the tab's content
            targetTab.setContent(content);
        } else {
            System.err.println("Tab with id " + tabId + " not found.");
        }
    }

    private void handleTabSelection(Tab selectedTab) {
        if (selectedTab.equals(aboutTab)) {
            loadAbout();
        } else if (selectedTab.equals(contactTab)) {
            loadContact();
        } else if (selectedTab.equals(chatroomTab)) {
            loadChatRoom();
        } else if (selectedTab.equals(newsTab)) {
            loadNews();
        }

        // Handle other tabs if needed
    }

    private void loadAbout() {
        try {
            loadTabContent(aboutTab.getId(), "About.fxml");
        } catch (IOException e) {
            System.out.println("About pane can not loaded");
        }
    }

    private void loadChatRoom() {
        try {
            loadTabContent(chatroomTab.getId(), "chatRoom-view.fxml");

        } catch (IOException e) {
            System.out.println("chat room pane can not loaded");
        }
    }

    private void loadContact() {
        try {
            loadTabContent(contactTab.getId(), "contact.fxml");
        } catch (IOException e) {
            System.out.println("contact pane can not loaded");
        }
    }

    private void loadNews() {
        try {
            loadTabContent(newsTab.getId(), "News.fxml");
        } catch (IOException e) {
            System.out.println("news pane can not loaded");
        }
    }

    private void setChatbotButtonAction(ActionEvent e) {
        if (buttonImage.getImage().getUrl().equals("file:/C:/Users/Lenovo/IdeaProjects/chatApp/target/classes/com/example/chatapp/images/chatbot1.png")) {
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                chatbotPane.getChildren().add(loader.load());
                chatbotPane.toFront();
                chatbotPane.setVisible(true);

                // Slide in and fade in animation
                slideAnchorPaneTo(chatbotPane, 1000, 0);
                applyFadeTransition(chatbotPane, 1000);
                applyFadeTransition(buttonImage, 800);

                buttonImage.setImage(new Image("file:/C:/Users/Lenovo/IdeaProjects/chatApp/target/classes/com/example/chatapp/images/cancel.png"));
                buttonImage.setFitWidth(25);
                buttonImage.setFitHeight(24);
            } catch (IOException ex) {
                System.out.println("problem in load chatbot");
            }
        } else {
            // Slide out animation first
            slideAnchorPaneTo(chatbotPane, 0, 1000);

            // Create a fade-out transition
            FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), chatbotPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Set action after fade-out finishes
            fadeOut.setOnFinished(event -> {
                chatbotPane.setVisible(false);
                chatbotPane.toBack();
            });

            fadeOut.play(); // Start fade-out animation

            // Fade out button image and change it
            applyFadeTransition(buttonImage, 800);
            buttonImage.setImage(new Image("file:/C:/Users/Lenovo/IdeaProjects/chatApp/target/classes/com/example/chatapp/images/chatbot1.png"));
            buttonImage.setFitWidth(42);
            buttonImage.setFitHeight(41);
        }
    }


    private void slideAnchorPaneTo(Node node, double from, double to) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), node);
        translateTransition.setFromX(from);
        translateTransition.setToX(to);
        translateTransition.play();
    }

    @FXML
    private void setArabic() {

        // Set translated texts to respective components
        hoursLabel.setText("ساعات العمل");
        welcomeLabel.setText("نتطلع إلى الترحيب بكم في المتحف المصري الكبير\n");
        introLabel.setText("يقدم مجمع GEM الآن جولات محدودة لاختبار جاهزية الموقع وتجربة الزائر قبل الافتتاح الرسمي.\n يقتصر الوصول حاليًا على القاعة الكبرى والدرج الكبير والمنطقة التجارية والحدائق الخارجية. جميع المساحات الداخلية الأخرى،\n بما في ذلك الوصول إلى المعارض والمجموعات، مقيدة\n" +
                "حتى الافتتاح الرسمي.");
        timeLabel.setText("الأحد إلى الخميس: 9 صباحًا - 6 مساءً\n" +
                "آخر موعد لشراء التذاكر: 4 مساءً\n" +
                "الجمعة والسبت: 9 صباحًا - 10 مساءً\n" +
                "آخر موعد لشراء التذاكر: 7:30 مساءً");
        activitesLabel.setText("أنشطة");
        pyramidLabel.setText("جولة الأهرامات");
        kidsLabel.setText("جولة الاطفال");
        GemLabel.setText("جولة المتحف");
        contactLabel.setText("غير راض عن إجابتنا؟");
        contactBtn.setText("روبوت الدردشة");
        homeTab.setText("الرئيسيه");
        newsTab.setText("الأخبار");
        aboutTab.setText("عن المتحف");
        bookingTab.setText("الحجز");
        contactTab.setText("تيك توك");
        newsLabel.setText("تصفح الأحداث القادمة واحصل على تذكرة قبل بيعها!");
        newsBtn.setText("الاخبار");
        bookingBtn.setText("الحجز");
        languageSelcetor.setText("الترجمه");
        chatroomTab.setText("غرفة الدردشه");

    }
    @FXML
    private void setEnglish() {
// Set translated texts to respective components
        hoursLabel.setText("Working Hours");
        welcomeLabel.setText("We look forward to welcoming you to the Grand Egyptian Museum\n");
        introLabel.setText("The GEM complex now offers limited tours to test site readiness and the visitor experience before the official opening. Currently, access is limited to the Grand Hall, the Grand Staircase, the commercial area, and the outdoor gardens. All other indoor areas, including access to exhibitions and collections, are restricted until the official opening.");
        timeLabel.setText("Sunday to Thursday: 9 AM - 6 PM\n" +
                "Last ticket purchase: 4 PM\n" +
                "Friday and Saturday: 9 AM - 10 PM\n" +
                "Last ticket purchase: 7:30 PM");
        activitesLabel.setText("Activities");
        pyramidLabel.setText("Pyramids Tour");
        kidsLabel.setText("Kids Tour");
        GemLabel.setText("Museum Tour");
        contactLabel.setText("Not satisfied with our answer?");
        contactBtn.setText("Chatbot");
        homeTab.setText("Home");
        newsTab.setText("News");
        aboutTab.setText("About the Museum");
        bookingTab.setText("Booking");
        contactTab.setText("TikTok");
        newsLabel.setText("Browse upcoming events and get a ticket before they sell out!");
        newsBtn.setText("News");
        bookingBtn.setText("Booking");
        languageSelcetor.setText("Translation");
        chatroomTab.setText("Chat Room");


    }
    }




