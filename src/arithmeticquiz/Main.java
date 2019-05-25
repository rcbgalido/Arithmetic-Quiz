package arithmeticquiz;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author rcbgalido
 */
public class Main extends javax.swing.JFrame {

    protected static final int ADDITION = 1;
    protected static final int SUBTRACTION = 2;
    protected static final int MULTIPLICATION = 3;
    protected static final int DIVISION = 4;
    
    protected static final int TOTAL_NUMBERS_PER_ROUND = 10;
    
    private static final String ADDITION_TEXT = "Addition";
    private static final String SUBTRACTION_TEXT = "Subtraction";
    private static final String MULTIPLICATION_TEXT = "Multiplication";
    private static final String DIVISION_TEXT = "Division";
    
    private static final String ADDITION_SYMBOL = "+";
    private static final String SUBTRACTION_SYMBOL = "-";
    private static final String MULTIPLICATION_SYMBOL = "*";
    private static final String DIVISION_SYMBOL = "/";
    
    private static final int QUIZ_TIME_IN_SECONDS = 20;
    private static final int ENTER_KEY_CODE = 10;
    private static final int MILLISECONDS_IN_A_SECOND = 1000;
    private static final int YES_OPTION = 0;
    private static final int TOTAL_ANSWERS_PER_ROUND = 5;
    private static final int BASE_10 = 10;
    
    private static final Color COLOR_LIGHT_GRAY = Color.getHSBColor(0, 0, 0.75f); 
    
    private final NumbersGenerator numbersGenerator;
    private final JTextField answerTextFields[];
    private int[] numbers;
    
    private int operation;
    private int timerInterval;
    private int roundCorrectAnswersCount, totalCorrectAnswersCount, totalSubmittedAnswersCount;
    
    private Timer timer;
    private ImageIcon titleImage;

    public Main() {
        initComponents();
        setLocationRelativeTo(null);
        
        numbersGenerator = new NumbersGenerator();
        
        selectOperationCHC.add(ADDITION_TEXT);
        selectOperationCHC.add(SUBTRACTION_TEXT);
        selectOperationCHC.add(MULTIPLICATION_TEXT);
        selectOperationCHC.add(DIVISION_TEXT);
        
        answerTextFields = new JTextField[]{
            answer1TF, answer2TF, answer3TF, answer4TF, answer5TF
        };
    }
    
    private void enableSelectOperationButton() {
        selectOperationBTN.setEnabled(true);
    }
    
    private void disableSelectOperationButton() {
        selectOperationBTN.setEnabled(false);
    }
    
    private void enableAnswerTextFields() {
        for (JTextField answerTextField : answerTextFields) {
            answerTextField.setFocusable(true);
        }
    }
    
    private void disableAnswerTextFields() {
        for (JTextField answerTextField : answerTextFields) {
            answerTextField.setFocusable(false);
        }
    }
    
    private void resetRoundCorrectAnswersCount() {
        roundCorrectAnswersCount = 0;
    }
    
    private void resetTotalCorrectAnswersCount() {
        totalCorrectAnswersCount = 0;
    }
    
    private void resetTotalSubmittedAnswersCount() {
        totalSubmittedAnswersCount = 0;
    }
    
    private void resetAnswerTextFieldsBackground() {
        for (JTextField answerTextField : answerTextFields) {
            answerTextField.setBackground(COLOR_LIGHT_GRAY);
        }
    }

    private void clearAnswers() {
        for (JTextField answerTextField : answerTextFields) {
            answerTextField.setText("");
        }
    }
    
    private void setQuestions() {
        String symbol = "";
        switch (selectOperationCHC.getSelectedItem()) {
            case ADDITION_TEXT:
                operation = ADDITION;
                symbol = ADDITION_SYMBOL;
                break;
            case SUBTRACTION_TEXT:
                operation = SUBTRACTION;
                symbol = SUBTRACTION_SYMBOL;
                break;
            case MULTIPLICATION_TEXT:
                operation = MULTIPLICATION;
                symbol = MULTIPLICATION_SYMBOL;
                break;
            case DIVISION_TEXT:
                operation = DIVISION;
                symbol = DIVISION_SYMBOL;
                break;
            default:
                break;
        }
        
        numbers = numbersGenerator.run(operation);
        
        question1TF.setText(numbers[0] + " " + symbol + " " + numbers[1]);
        question2TF.setText(numbers[2] + " " + symbol + " " + numbers[3]);
        question3TF.setText(numbers[4] + " " + symbol + " " + numbers[5]);
        question4TF.setText(numbers[6] + " " + symbol + " " + numbers[7]);
        question5TF.setText(numbers[8] + " " + symbol + " " + numbers[9]);
    }
    
    private void startTimer() {
        timer = new Timer();
        timerInterval = QUIZ_TIME_IN_SECONDS + 1;
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                timerTF.setText("" + setTimerInterval());
            }
        }, MILLISECONDS_IN_A_SECOND, MILLISECONDS_IN_A_SECOND);
    }
    
    private void stopTimer() {
        timer.cancel();
    }
    
    private int setTimerInterval() {
        if (timerInterval == 0) { // time's up!
            stopTimer();
            setNonIntegerAnswersToZero();
            submitAnswersBTNActionPerformed(null);
            return 0;
        } else {
            return --timerInterval;
        }
    }
    
    private void enableSubmitAnswersButton() {
        submitAnswersBTN.setEnabled(true);
    }
    
    private void disableSubmitAnswersButton() {
        submitAnswersBTN.setEnabled(false);
    }
    
    private void enableResetAccuracyButton() {
        resetAccuracyBTN.setEnabled(true);
    }
    
    private void disableResetAccuracyButton() {
        resetAccuracyBTN.setEnabled(false);
    }
    
    private void setNonIntegerAnswersToZero() {
        for (JTextField answerTextField : answerTextFields) {
            String answer = answerTextField.getText();
            if (isInteger(answer) == false) {
                answerTextField.setText("0");
            }
        }
    }
    
    private boolean isInteger(String str) {
        if(str.isEmpty()) return false;
        for(int i = 0; i < str.length(); i++) {
            if(i == 0 && str.charAt(i) == '-') {
                if(str.length() == 1) return false;
                else continue;
            }
            if(Character.digit(str.charAt(i), BASE_10) < 0) return false;
        }
        return true;
    }
    
    private void updateAccuracy() {
        double accuracy = (Double.parseDouble(totalCorrectAnswersCount + "") / totalSubmittedAnswersCount) * 100;
        accuracy = Double.valueOf(new DecimalFormat(".##").format(accuracy));
        accuracyTF.setText(accuracy + "%");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        accuracyTF = new javax.swing.JTextField();
        resetAccuracyBTN = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        selectOperationCHC = new java.awt.Choice();
        selectOperationBTN = new javax.swing.JButton();
        timerTF = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        answer1TF = new javax.swing.JTextField();
        answer2TF = new javax.swing.JTextField();
        answer3TF = new javax.swing.JTextField();
        answer4TF = new javax.swing.JTextField();
        answer5TF = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        question1TF = new javax.swing.JTextField();
        question2TF = new javax.swing.JTextField();
        question3TF = new javax.swing.JTextField();
        question4TF = new javax.swing.JTextField();
        question5TF = new javax.swing.JTextField();
        submitAnswersBTN = new javax.swing.JButton();
        titleImage=new ImageIcon(getClass().getResource("resources/01_title.png"));
        jLabel4 = new javax.swing.JLabel(titleImage);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Arithmetic Quiz");
        setLocationByPlatform(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(91, 125, 135));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(46, 50, 60), 10));

        jPanel2.setBackground(new java.awt.Color(91, 125, 135));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jLabel1.setText("Accuracy:");

        accuracyTF.setEditable(false);
        accuracyTF.setBackground(new java.awt.Color(204, 204, 204));
        accuracyTF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        accuracyTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        accuracyTF.setText("--");
        accuracyTF.setFocusable(false);

        resetAccuracyBTN.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        resetAccuracyBTN.setText("Reset");
        resetAccuracyBTN.setEnabled(false);
        resetAccuracyBTN.setFocusable(false);
        resetAccuracyBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetAccuracyBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(accuracyTF))
                    .addComponent(resetAccuracyBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(accuracyTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetAccuracyBTN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(91, 125, 135));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jLabel2.setText("Select Operation");

        selectOperationCHC.setBackground(new java.awt.Color(204, 204, 204));
        selectOperationCHC.setFocusable(false);
        selectOperationCHC.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N

        selectOperationBTN.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        selectOperationBTN.setText("Select");
        selectOperationBTN.setFocusable(false);
        selectOperationBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectOperationBTNActionPerformed(evt);
            }
        });

        timerTF.setEditable(false);
        timerTF.setBackground(java.awt.Color.lightGray);
        timerTF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        timerTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timerTF.setFocusable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectOperationCHC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectOperationBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(timerTF, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel2)
                .addGap(37, 37, 37))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectOperationCHC, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectOperationBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(timerTF, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(91, 125, 135));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel6.setBackground(new java.awt.Color(91, 125, 135));

        answer1TF.setBackground(java.awt.Color.lightGray);
        answer1TF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        answer1TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        answer1TF.setFocusable(false);
        answer1TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Main.this.keyPressed(evt);
            }
        });

        answer2TF.setBackground(java.awt.Color.lightGray);
        answer2TF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        answer2TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        answer2TF.setFocusable(false);
        answer2TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Main.this.keyPressed(evt);
            }
        });

        answer3TF.setBackground(java.awt.Color.lightGray);
        answer3TF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        answer3TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        answer3TF.setFocusable(false);
        answer3TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Main.this.keyPressed(evt);
            }
        });

        answer4TF.setBackground(java.awt.Color.lightGray);
        answer4TF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        answer4TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        answer4TF.setFocusable(false);
        answer4TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Main.this.keyPressed(evt);
            }
        });

        answer5TF.setBackground(java.awt.Color.lightGray);
        answer5TF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        answer5TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        answer5TF.setFocusable(false);
        answer5TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Main.this.keyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(answer1TF)
                    .addComponent(answer2TF)
                    .addComponent(answer3TF)
                    .addComponent(answer4TF)
                    .addComponent(answer5TF, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(answer1TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(answer2TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(answer3TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(answer4TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(answer5TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel5.setBackground(new java.awt.Color(91, 125, 135));

        question1TF.setEditable(false);
        question1TF.setBackground(java.awt.Color.lightGray);
        question1TF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        question1TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        question1TF.setFocusable(false);

        question2TF.setEditable(false);
        question2TF.setBackground(java.awt.Color.lightGray);
        question2TF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        question2TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        question2TF.setFocusable(false);

        question3TF.setEditable(false);
        question3TF.setBackground(java.awt.Color.lightGray);
        question3TF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        question3TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        question3TF.setFocusable(false);

        question4TF.setEditable(false);
        question4TF.setBackground(java.awt.Color.lightGray);
        question4TF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        question4TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        question4TF.setFocusable(false);

        question5TF.setEditable(false);
        question5TF.setBackground(java.awt.Color.lightGray);
        question5TF.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        question5TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        question5TF.setFocusable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(question5TF)
                    .addComponent(question4TF)
                    .addComponent(question3TF)
                    .addComponent(question2TF)
                    .addComponent(question1TF, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(question1TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(question2TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(question3TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(question4TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(question5TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        submitAnswersBTN.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        submitAnswersBTN.setText("Submit Answers");
        submitAnswersBTN.setEnabled(false);
        submitAnswersBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitAnswersBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(submitAnswersBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(submitAnswersBTN)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resetAccuracyBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetAccuracyBTNActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Confirm Reset Accuracy", "Arithmetic Quiz", JOptionPane.YES_NO_OPTION);
        if (confirm == YES_OPTION) {
            resetTotalCorrectAnswersCount();
            resetTotalSubmittedAnswersCount();
            disableResetAccuracyButton();
            accuracyTF.setText("--");
        }
    }//GEN-LAST:event_resetAccuracyBTNActionPerformed

    private void selectOperationBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectOperationBTNActionPerformed
        disableSelectOperationButton();
        enableAnswerTextFields();
        resetRoundCorrectAnswersCount();
        resetAnswerTextFieldsBackground();
        clearAnswers();
        setQuestions();
        startTimer();
        enableSubmitAnswersButton();
    }//GEN-LAST:event_selectOperationBTNActionPerformed

    private void keyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyPressed
        if (evt.getKeyCode() == ENTER_KEY_CODE) {
            submitAnswersBTNActionPerformed(null);
        }
    }//GEN-LAST:event_keyPressed

    private void submitAnswersBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitAnswersBTNActionPerformed
        stopTimer();
        
        setNonIntegerAnswersToZero();
        int[] answers = {
                Integer.parseInt(answer1TF.getText()),
                Integer.parseInt(answer2TF.getText()),
                Integer.parseInt(answer3TF.getText()),
                Integer.parseInt(answer4TF.getText()),
                Integer.parseInt(answer5TF.getText())
            };

        for (int a = 0; a < TOTAL_NUMBERS_PER_ROUND; a = a + 2) {
            int correctAnswer = 0;

            switch (operation) {
                case ADDITION:
                    correctAnswer = numbers[a] + numbers[a + 1];
                    break;
                case SUBTRACTION:
                    correctAnswer = numbers[a] - numbers[a + 1];
                    break;
                case MULTIPLICATION:
                    correctAnswer = numbers[a] * numbers[a + 1];
                    break;
                case DIVISION:
                    correctAnswer = numbers[a] / numbers[a + 1];
                    break;
                default:
                    break;
            }

            if (correctAnswer == answers[a / 2]) { // inputted answer is correct
                answerTextFields[a / 2].setBackground(Color.YELLOW);
                roundCorrectAnswersCount++;
            } else { // inputted answer is incorrect
                answerTextFields[a / 2].setBackground(Color.red);
            }
        }

        totalCorrectAnswersCount += roundCorrectAnswersCount;
        totalSubmittedAnswersCount += TOTAL_ANSWERS_PER_ROUND;

        updateAccuracy();
        disableSubmitAnswersButton();
        disableAnswerTextFields();
        enableResetAccuracyButton();
        enableSelectOperationButton();
        
        JOptionPane.showMessageDialog(null, "You got " + roundCorrectAnswersCount + " out of " + 
                TOTAL_ANSWERS_PER_ROUND + "!");

    }//GEN-LAST:event_submitAnswersBTNActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accuracyTF;
    private javax.swing.JTextField answer1TF;
    private javax.swing.JTextField answer2TF;
    private javax.swing.JTextField answer3TF;
    private javax.swing.JTextField answer4TF;
    private javax.swing.JTextField answer5TF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField question1TF;
    private javax.swing.JTextField question2TF;
    private javax.swing.JTextField question3TF;
    private javax.swing.JTextField question4TF;
    private javax.swing.JTextField question5TF;
    private javax.swing.JButton resetAccuracyBTN;
    private javax.swing.JButton selectOperationBTN;
    private java.awt.Choice selectOperationCHC;
    private javax.swing.JButton submitAnswersBTN;
    private javax.swing.JTextField timerTF;
    // End of variables declaration//GEN-END:variables
}
