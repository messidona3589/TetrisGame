import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
class Image{
    static ImageIcon IMino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/I미노.png");
    static ImageIcon JMino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/J미노.png");
    static ImageIcon LMino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/L미노.png");
    static ImageIcon OMino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/O미노.png");
    static ImageIcon SMino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/S미노.png");
    static ImageIcon TMino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/T미노.png");
    static ImageIcon ZMino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/Z미노.png");
    static ImageIcon basicImage = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/한칸.png");
    static ImageIcon Imino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/IMino.png");
    static ImageIcon Omino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/OMino.png");
    static ImageIcon Tmino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/TMino.png");
    static ImageIcon Jmino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/JMino.png");
    static ImageIcon Lmino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/LMino.png");
    static ImageIcon Smino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/SMino.png");
    static ImageIcon Zmino = new ImageIcon("C:/Users/messi/IdeaProjects/TetrisGame/src/Image/ZMino.png");

} //각 블록 한칸 이미지
class tetrisGame{
    // GUI 설정
    public JPanel tetrisPanel = new JPanel();
    public JLabel blockLabel[][] = new JLabel[20][10];
    public JFrame tetrisFrame = new JFrame("TETRIS");
    public JLabel currentBlockLabel = new JLabel(); //현재 블록 보여주기
    public JLabel currentBlockWordLabel = new JLabel(); //"현재 블록"
    public JLabel nextBlockLabel = new JLabel(); //다음 블록 미리 보여주기
    public JLabel nextBlockWordLabel = new JLabel(); //"다음 블록"
    public JLabel holdBlockLabel = new JLabel(); //홀드한 블록 보여주기
    public JLabel holdBlockWordLabel = new JLabel(); //"홀드 블록"
    public JLabel scoreLabel = new JLabel(); //점수 보여주기
    public JLabel rankWordLabel = new JLabel(); //"개인 랭킹"
    public JLabel rank1Label = new JLabel(); //1위
    public JLabel rank2Label = new JLabel(); //2위
    public JLabel rank3Label = new JLabel(); //3위
    public JLabel rank4Label = new JLabel(); //4위
    public JLabel rank5Label = new JLabel(); //5위


    //기능 변수 모음
    public int dp[][] = new int[20][10]; //블록 있으면 1, 없으면 0
    public Random rnd = new Random(); //랜덤 메서드에서 사용할 객체 생성
    public int randomNumberCheck[] = new int[8]; //7개 종류의 블록이 랜덤으로 한세트씩 나와야됨(0번째는 없음)
    public int currentBlockIdx; //현재 블록의 idx 1~7, 0은 빈칸
    public int nextBlockIdx; //다음 블록의 idx
    public int currentBlockArray[][] = new int[4][2]; //현재 블록의 좌표
    public int turnCnt = 0; //현재 블록이 몇번 회전했는지 (0~3) 시계방향 기준!
    public int holdBlockIdx = 0; //홀드 된 블록 번호
    public int holdCnt = 0; //홀드 몇번 했는지 세기(최대 3번까지)
    public int mark = 0; //현재 점수
    public int markList[] = new int[5]; //점수 배열
    public String nameList[] = new String[5]; //이름 배열
    public String name; //현재 사용자 이름
    public int timerStop = 0; //타이머 멈추게 하는 것
    static int firstblockArray[][][] = new int[][][] { //처음 블록 만들어지는 모양
            {},
        {
            {-1, 3}, {-1, 4}, {-1, 5}, {-1, 6} //_블럭 I
        },
        {
            {-2, 4}, {-2, 5}, {-1, 4}, {-1, 5} //ㅁ블럭 O
        },
        {
            {-2, 4}, {-1, 3}, {-1, 4}, {-1, 5} //ㅗ블럭 T
        },
        {
            {-2, 3}, {-1, 3}, {-1, 4}, {-1, 5} //ㅣ__블럭 J
        },
        {
            {-2, 5}, {-1, 3}, {-1, 4}, {-1, 5} //__ㅣ 블럭 L
        },
        {
            {-2, 4}, {-2, 5}, {-1, 3}, {-1, 4} //_ㅣ-블럭 S
        },
        {
            {-2, 3}, {-2, 4}, {-1, 4}, {-1, 5} //-ㅣ_블럭 Z
        }
    };

    //메서드 모음
    public void randomBlockGenerator(){
        // 블록 종류 세트 아직 다 나왔으면 모두 0으로 초기화
        int sum = 0;
        for (int i=1;i<8;i++){sum += randomNumberCheck[i];}
        if (sum==7) randomNumberCheck = new int[8];

        turnCnt = 0;

        // 랜덤 함수 적용
        int temp;
        while(true){
            temp = rnd.nextInt(7) + 1;
            if (randomNumberCheck[temp]==0){ //한 세트 안에서 아직 안나온 블록이라면
                //다음 블록을 현재 블록으로 불러오기
                currentBlockIdx = nextBlockIdx;
                currentBlockArray = firstblockArray[currentBlockIdx]; //최초 블록의 좌표 넣기
                switch (currentBlockIdx){
                    case 1:
                        currentBlockLabel.setIcon(Image.Imino);
                        break;
                    case 2:
                        currentBlockLabel.setIcon(Image.Omino);
                        break;
                    case 3:
                        currentBlockLabel.setIcon(Image.Tmino);
                        break;
                    case 4:
                        currentBlockLabel.setIcon(Image.Jmino);
                        break;
                    case 5:
                        currentBlockLabel.setIcon(Image.Lmino);
                        break;
                    case 6:
                        currentBlockLabel.setIcon(Image.Smino);
                        break;
                    case 7:
                        currentBlockLabel.setIcon(Image.Zmino);
                        break;
                } //현재블록 보여주기

                //다음블록 랜덤 뽑기
                randomNumberCheck[temp] = 1;
                nextBlockIdx = temp;
                switch (nextBlockIdx){
                    case 1:
                        nextBlockLabel.setIcon(Image.Imino);
                        break;
                    case 2:
                        nextBlockLabel.setIcon(Image.Omino);
                        break;
                    case 3:
                        nextBlockLabel.setIcon(Image.Tmino);
                        break;
                    case 4:
                        nextBlockLabel.setIcon(Image.Jmino);
                        break;
                    case 5:
                        nextBlockLabel.setIcon(Image.Lmino);
                        break;
                    case 6:
                        nextBlockLabel.setIcon(Image.Smino);
                        break;
                    case 7:
                        nextBlockLabel.setIcon(Image.Zmino);
                        break;
                } //다음 블록 보여주기
                break;
            }
        }
    }
    public void startNewGame(){
        for (int i=0;i<20;i++){
            for (int j=0;j<10;j++){
                blockLabel[i][j] = new JLabel(Image.basicImage);
                blockLabel[i][j].setBorder(new LineBorder(Color.BLACK));
                tetrisPanel.add(blockLabel[i][j]);
            }
        }
        tetrisPanel.setLayout(new GridLayout(20,10));
        tetrisPanel.setBounds(200, 30, 400, 800);


        tetrisFrame.add(tetrisPanel);
       tetrisFrame.setSize(800, 1000);// 프레임 크기 설정
       tetrisFrame.setLocationRelativeTo(null);// 프레임을 화면 가운데에 배치
       tetrisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임을 닫았을 때 메모리에서 제거되도록 설정
       tetrisFrame.setLayout(null);
       tetrisFrame.setVisible(true);// 프레임이 보이도록 설정
       tetrisFrame.setResizable(false);
       tetrisFrame.addKeyListener(new KeyListener() {
           @Override
           public void keyTyped(KeyEvent e) {}
           @Override
           public void keyPressed(KeyEvent e) {
               switch (e.getKeyCode()){
                   case KeyEvent.VK_LEFT:
                       moveLeft();
                       break;
                   case KeyEvent.VK_RIGHT:
                       moveRight();
                       break;
                   case KeyEvent.VK_DOWN:
                       moveDown();
                       break;
                   case KeyEvent.VK_SPACE:
                       hardDrop();
                       break;
                   case KeyEvent.VK_UP:
                       turnClockwise();
                       break;
                   case KeyEvent.VK_A:
                       turnClockwise();
                       break;
                   case KeyEvent.VK_CONTROL:
                       turnAntiClockwise();
                       break;
                   case KeyEvent.VK_Z:
                       turnAntiClockwise();
                       break;
                   case KeyEvent.VK_SHIFT:
                       if (holdCnt!=3) holdBlock(); //홀드는 최대 3번까지
                       else JOptionPane.showMessageDialog(null, "홀드 불가!", "ERROR", JOptionPane.WARNING_MESSAGE);
                       break;
               }
           }
           @Override
           public void keyReleased(KeyEvent e) {}
       });

       JOptionPane.showMessageDialog(null, "방향키 좌우 이동\n방향키 아래 소프트드롭\n방향키 위, A 시계방향 회전\nCONTROL, Z 반시계방향 회전\nSPACE 하드드롭\nSHIFT 홀드", "설명서", JOptionPane.INFORMATION_MESSAGE);

       name = JOptionPane.showInputDialog("이름을 입력하시오.");
       tetrisFrame.add(scoreLabel);
       scoreLabel.setBounds(350, 850, 300, 50);
       scoreLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
       scoreLabel.setText(name +" : " + mark + " 점");

       tetrisFrame.add(currentBlockLabel);
       currentBlockLabel.setBounds(630, 60, 150, 150);
       tetrisFrame.add(currentBlockWordLabel);
       currentBlockWordLabel.setText("현재 블록");
       currentBlockWordLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
       currentBlockWordLabel.setBounds(630, -10, 100, 100);

        tetrisFrame.add(nextBlockLabel);
        nextBlockLabel.setBounds(630, 270, 150, 150);
        tetrisFrame.add(nextBlockWordLabel);
        nextBlockWordLabel.setText("다음 블록");
        nextBlockWordLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        nextBlockWordLabel.setBounds(630, 200, 100, 100);

        tetrisFrame.add(holdBlockLabel);
        holdBlockLabel.setBounds(50, 60, 150, 150);
        tetrisFrame.add(holdBlockWordLabel);
        holdBlockWordLabel.setText("홀드 블록");
        holdBlockWordLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        holdBlockWordLabel.setBounds(50, -10, 100, 100);

        tetrisFrame.add(rankWordLabel);
        tetrisFrame.add(rank1Label);
        tetrisFrame.add(rank2Label);
        tetrisFrame.add(rank3Label);
        tetrisFrame.add(rank4Label);
        tetrisFrame.add(rank5Label);
        rankWordLabel.setText("개인 랭킹");
        rankWordLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        rankWordLabel.setBounds(50, 270, 100, 50);
        rank1Label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        rank1Label.setBounds(50, 320, 100, 50);
        rank2Label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        rank2Label.setBounds(50, 370, 100, 50);
        rank3Label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        rank3Label.setBounds(50, 420, 100, 50);
        rank4Label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        rank4Label.setBounds(50, 470, 100, 50);
        rank5Label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        rank5Label.setBounds(50, 520, 100, 50);


        //다음 블록 랜덤 생성 그리고 randomGenerator 실행
        int temp = rnd.nextInt(7) + 1;
        randomNumberCheck[temp] = 1;
        nextBlockIdx = temp;
        randomBlockGenerator();

        makeTimer();
    }
    public void holdBlock(){
        //현재 블록 위치 색깔 0으로 만들고 그림판에 띄우고 다음 블록을 현재 블록으로
        if (holdBlockIdx==0){
            changeColor(currentBlockArray, 0);
            holdBlockIdx = currentBlockIdx;
            switch (holdBlockIdx){
                case 1:
                    holdBlockLabel.setIcon(Image.Imino);
                    break;
                case 2:
                    holdBlockLabel.setIcon(Image.Omino);
                    break;
                case 3:
                    holdBlockLabel.setIcon(Image.Tmino);
                    break;
                case 4:
                    holdBlockLabel.setIcon(Image.Jmino);
                    break;
                case 5:
                    holdBlockLabel.setIcon(Image.Lmino);
                    break;
                case 6:
                    holdBlockLabel.setIcon(Image.Smino);
                    break;
                case 7:
                    holdBlockLabel.setIcon(Image.Zmino);
                    break;
            } //홀드 블록 보여주기
            //다음 블록을 현재 블록으로
            randomBlockGenerator();
        }
        else{
            randomNumberCheck[nextBlockIdx] = 0;
            nextBlockIdx = holdBlockIdx;
            holdBlockIdx = 0;
            holdBlockLabel.setIcon(null);
            holdCnt++;
            switch (nextBlockIdx){
                case 1:
                    nextBlockLabel.setIcon(Image.Imino);
                    break;
                case 2:
                    nextBlockLabel.setIcon(Image.Omino);
                    break;
                case 3:
                    nextBlockLabel.setIcon(Image.Tmino);
                    break;
                case 4:
                    nextBlockLabel.setIcon(Image.Jmino);
                    break;
                case 5:
                    nextBlockLabel.setIcon(Image.Lmino);
                    break;
                case 6:
                    nextBlockLabel.setIcon(Image.Smino);
                    break;
                case 7:
                    nextBlockLabel.setIcon(Image.Zmino);
                    break;
            } //다음 블록 보여주기
        }
        //랜덤 블록 생성 함수 호출
        //홀드된 블록 없으면 블록 홀드
        //홀드된 블록 있으면 블록 사용 단 현재 바로 나오는 것이 아니라 다음 블록으로 설정
        //원래 다음 블록은 기록에서 삭제....
    }
    public void makeTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask(){
            @Override
            public void run() {
                if (timerStop==0) moveDown();
            }
        };
        timer.schedule(timerTask,0,1000);
    }
    public void changeColor(int array[][], int currentBlockIdx){
        switch(currentBlockIdx){
            case 0:
                for (int i=0;i<4;i++){
                    if (0<=array[i][0] && array[i][0]<20 && 0<=array[i][1] && array[i][1]<10){
                        blockLabel[array[i][0]][array[i][1]].setIcon(Image.basicImage);
                    }
                }
                break;
            case 1:
                for (int i=0;i<4;i++){
                    if (0<=array[i][0] && array[i][0]<20 && 0<=array[i][1] && array[i][1]<10){
                        blockLabel[array[i][0]][array[i][1]].setIcon(Image.IMino);
                    }
                }
                break;
            case 2:
                for (int i=0;i<4;i++){
                    if (0<=array[i][0] && array[i][0]<20 && 0<=array[i][1] && array[i][1]<10){
                        blockLabel[array[i][0]][array[i][1]].setIcon(Image.OMino);
                    }
                }
                break;
            case 3:
                for (int i=0;i<4;i++){
                    if (0<=array[i][0] && array[i][0]<20 && 0<=array[i][1] && array[i][1]<10){
                        blockLabel[array[i][0]][array[i][1]].setIcon(Image.TMino);
                    }
                }
                break;
            case 4:
                for (int i=0;i<4;i++){
                    if (0<=array[i][0] && array[i][0]<20 && 0<=array[i][1] && array[i][1]<10){
                        blockLabel[array[i][0]][array[i][1]].setIcon(Image.JMino);
                    }
                }
                break;
            case 5:
                for (int i=0;i<4;i++){
                    if (0<=array[i][0] && array[i][0]<20 && 0<=array[i][1] && array[i][1]<10){
                        blockLabel[array[i][0]][array[i][1]].setIcon(Image.LMino);
                    }
                }
                break;
            case 6:
                for (int i=0;i<4;i++){
                    if (0<=array[i][0] && array[i][0]<20 && 0<=array[i][1] && array[i][1]<10){
                        blockLabel[array[i][0]][array[i][1]].setIcon(Image.SMino);
                    }
                }
                break;
            case 7:
                for (int i=0;i<4;i++){
                    if (0<=array[i][0] && array[i][0]<20 && 0<=array[i][1] && array[i][1]<10){
                        blockLabel[array[i][0]][array[i][1]].setIcon(Image.ZMino);
                    }
                }
                break;
        }
    }
    public void hardDrop(){
        int newBlockArray[][] = new int[4][2];
        int k=1;

        while(true){
            int checkFlag = 1;
            for (int i=0;i<4;i++) {
                int nx = currentBlockArray[i][0] + k;
                int ny = currentBlockArray[i][1];

                if (nx<20){
                    if (nx<0) continue; //위에 걸리는 부분
                    if (dp[nx][ny]!=0){ //다른 블록 만남
                        checkFlag = 0;
                        break;
                    }
                } else { //바닥 만남
                    checkFlag = 0;
                    break;
                }
            }
            if (checkFlag==0) break;
            k++;
        }

        //다 내려왔으므로 dp 최신화, 새로운 배열로
        for (int i=0;i<4;i++){
            newBlockArray[i][0] = currentBlockArray[i][0] + k-1;
            newBlockArray[i][1] = currentBlockArray[i][1];
            dp[newBlockArray[i][0]][newBlockArray[i][1]] = 1;
        }
        changeColor(currentBlockArray, 0); //원래 있던 자리는 기본 색으로
        changeColor(newBlockArray, currentBlockIdx); //이동 후 자리는 새로운 색으로
        currentBlockArray = newBlockArray; //블록 위치 없데이트
        clearRow();
        randomBlockGenerator();
    }
    public void moveDown(){
        int newBlockArray[][] = new int[4][2];
        int checkFlag = 1;

        for (int i=0;i<4;i++) {
            int nx = newBlockArray[i][0] = currentBlockArray[i][0] + 1;
            int ny = newBlockArray[i][1] = currentBlockArray[i][1];

            if (nx<20){
                if (nx<0){ continue;}
                if (dp[nx][ny]!=0){ //다른 블록 만나면 현재 블록 고정
                    for (int j=0;j<4;j++){//만약 블록이 위로 넘치면 gameOver 실행
                        int x = currentBlockArray[j][0], y = currentBlockArray[j][0];
                        if (x<=0) {
                            changeColor(currentBlockArray, currentBlockIdx);
                            gameOver();
                            return;
                        }
                    }
                    checkFlag = 0;
                    break;
                }
            }
            else{
                checkFlag = 0;
                break;
            }
        }

        if (checkFlag==1){ //내려오는 거 가능할때 색깔 바꿔주기
            changeColor(currentBlockArray, 0); //원래 있던 자리는 기본 색으로
            changeColor(newBlockArray, currentBlockIdx); //새로운 자리는 색깔 입히기
            currentBlockArray = newBlockArray; //블록 위치 없데이트
        } else { //내려오는 거 불가능할때 clearRow()체크, 랜덤 블록 생성 하기
            for (int j=0;j<4;j++){dp[currentBlockArray[j][0]][currentBlockArray[j][1]] = currentBlockIdx;}
            clearRow();
            randomBlockGenerator();
        }
    }
    public void moveRight(){
        int newBlockArray[][] = new int[4][2];

        int checkFlag = 1;
        for (int i=0;i<4;i++) {
            int nx = newBlockArray[i][0] = currentBlockArray[i][0];
            int ny = newBlockArray[i][1] = currentBlockArray[i][1]+1;

            if (nx<20){
                if (nx<0) continue;
                if (dp[nx][ny]!=0){
                    checkFlag = 0;
                    break;
                }
            }
            else{
                checkFlag = 0;
                break;
            }
        }
        //가능하다면 dp 말고 색깔만 입히고, currentBlockArray 최신화
        if (checkFlag==1){
            changeColor(currentBlockArray, 0); //원래 있던 자리는 기본 색으로
            changeColor(newBlockArray, currentBlockIdx); //회전 후 자리는 새로운 색으로
            currentBlockArray = newBlockArray; //블록 위치 없데이트
        }
    }
    public void moveLeft(){
        int newBlockArray[][] = new int[4][2];

        int checkFlag = 1;
        for (int i=0;i<4;i++) {
            int nx = newBlockArray[i][0] = currentBlockArray[i][0];
            int ny = newBlockArray[i][1] = currentBlockArray[i][1]-1;

            if (nx<20){
                if (nx<0) continue;
                if (dp[nx][ny]!=0){
                    checkFlag = 0;
                    break;
                }
            }
            else{
                checkFlag = 0;
                break;
            }
        }
        //가능하다면 dp 말고 색깔만 입히고, currentBlockArray 최신화
        if (checkFlag==1){
            changeColor(currentBlockArray, 0); //원래 있던 자리는 기본 색으로
            changeColor(newBlockArray, currentBlockIdx); //회전 후 자리는 새로운 색으로
            currentBlockArray = newBlockArray; //블록 위치 없데이트
        }
    }
    public void turnClockwise(){
        int newBlockArray[][] = new int[4][2];
        int x = currentBlockArray[0][0], y = currentBlockArray[0][1];

        //시계방향 블록 회전 실행
        if (currentBlockIdx==1){
            if (turnCnt==0) {
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y+2;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y+2;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+2;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+2;
            }
            else if (turnCnt==1){
                newBlockArray[0][0] = x+2; newBlockArray[0][1] = y-2;
                newBlockArray[1][0] = x+2; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+2; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-2; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x-1; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else {
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+2;
            }
        }
        else if (currentBlockIdx==2){
            newBlockArray = currentBlockArray;
        }
        else if (currentBlockIdx==3){
            if (turnCnt==0) {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y;
            }
            else if (turnCnt==1){
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
        }
        else if (currentBlockIdx==4){
            if (turnCnt==0) {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y+2;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==1){
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
        }
        else if (currentBlockIdx==5){
            if (turnCnt==0) {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+2; newBlockArray[2][1] = y-1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y;
            }
            else if (turnCnt==1){
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y-1;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y;
                newBlockArray[1][0] = x-1; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y+2;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+2;
            }
        }
        else if (currentBlockIdx==6){
            if (turnCnt==0) {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==1){
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x+2; newBlockArray[2][1] = y-1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y;
            }
            else {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y+2;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
        }
        else if (currentBlockIdx==7){
            if (turnCnt==0) {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y+2;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+2;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==1){
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y-2;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+2; newBlockArray[2][1] = y-1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y;
            }
            else {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
        }

        //회전 가능한 지 체크
        int checkFlag = 1; //1이면 통과, 아니면 회전불가
        for(int i=0;i<4;i++){
            int nx = newBlockArray[i][0];
            int ny = newBlockArray[i][1];
            if (nx<20 && 0<=ny && ny<10){
                if (nx<0) continue;
                if (dp[nx][ny]!=0){
                    checkFlag = 0;
                    break;
                }
            }else{
                checkFlag = 0;
                break;
            }
        }

        //회전가능하다면 dp 말고 색깔만 입히고, currentBlockArray 최신화
        if (checkFlag==1){
            //회전 횟수 +1
            turnCnt = (turnCnt==3) ? 0 : turnCnt+1;
            changeColor(currentBlockArray, 0); //원래 있던 자리는 기본 색으로
            changeColor(newBlockArray, currentBlockIdx); //회전 후 자리는 새로운 색으로
            currentBlockArray = newBlockArray; //블록 위치 없데이트
        }
    }
    public void turnAntiClockwise(){
        int newBlockArray[][] = new int[4][2];
        int x = currentBlockArray[0][0], y = currentBlockArray[0][1];

        //반 시계방향 블록 회전 실행
        if (currentBlockIdx==1){
            if (turnCnt==0) {
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==1) {
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y-2;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-2; newBlockArray[0][1] = y+2;
                newBlockArray[1][0] = x-1; newBlockArray[1][1] = y+2;
                newBlockArray[2][0] = x; newBlockArray[2][1] = y+2;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+2;
            }
            else if (turnCnt==3){
                newBlockArray[0][0] = x+2; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+2; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+2; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+2;
            }
        }
        else if (currentBlockIdx==2){
            newBlockArray = currentBlockArray;
        }
        else if (currentBlockIdx==3){
            if (turnCnt==0) {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y;
            }
            else if(turnCnt==1){
                newBlockArray[0][0] = x; newBlockArray[0][1] = y;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x; newBlockArray[2][1] = y+2;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==3){
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y;
            }
        }
        else if (currentBlockIdx==4){
            if (turnCnt==0) {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x+2; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==1){
                newBlockArray[0][0] = x; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x-1; newBlockArray[1][1] = y+2;
                newBlockArray[2][0] = x; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==3){
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+1;
            }
        }
        else if (currentBlockIdx==5){
            if (turnCnt==0) {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y-2;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y-1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y-1;
            }
            else if (turnCnt==1){
                newBlockArray[0][0] = x; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+2;
            }
            else if (turnCnt==3){
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+2;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y;
            }
        }
        else if (currentBlockIdx==6){
            if (turnCnt==0) {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y;
            }
            else if(turnCnt==1){
                newBlockArray[0][0] = x; newBlockArray[0][1] = y;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y-1;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==3){
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y+2;
                newBlockArray[2][0] = x+2; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+1;
            }
        }
        else if (currentBlockIdx==7){
            if (turnCnt==0) {
                newBlockArray[0][0] = x; newBlockArray[0][1] = y+1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y+1;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y;
            }
            else if(turnCnt==1){
                newBlockArray[0][0] = x; newBlockArray[0][1] = y-2;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y-1;
                newBlockArray[2][0] = x+1; newBlockArray[2][1] = y-1;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y;
            }
            else if (turnCnt==2){
                newBlockArray[0][0] = x-1; newBlockArray[0][1] = y+2;
                newBlockArray[1][0] = x; newBlockArray[1][1] = y+1;
                newBlockArray[2][0] = x; newBlockArray[2][1] = y+2;
                newBlockArray[3][0] = x+1; newBlockArray[3][1] = y+1;
            }
            else if (turnCnt==3){
                newBlockArray[0][0] = x+1; newBlockArray[0][1] = y-1;
                newBlockArray[1][0] = x+1; newBlockArray[1][1] = y;
                newBlockArray[2][0] = x+2; newBlockArray[2][1] = y;
                newBlockArray[3][0] = x+2; newBlockArray[3][1] = y+1;
            }
        }

        //회전 가능한 지 체크
        int checkFlag = 1; //1이면 통과, 아니면 회전불가
        for(int i=0;i<4;i++){
            int nx = newBlockArray[i][0];
            int ny = newBlockArray[i][1];
            if (nx<20 && 0<=ny && ny<10){
                if (nx<0) continue;
                if (dp[nx][ny]!=0){
                    checkFlag = 0;
                    break;
                }
            }else{
                checkFlag = 0;
                break;
            }
        }

        //회전가능하다면 dp 말고 색깔만 입히고, currentBlockArray 최신화
        if (checkFlag==1){
            //반시계방향은 회전방향과 반대
            turnCnt = (turnCnt==0) ? 3 : turnCnt-1;
            changeColor(currentBlockArray, 0); //원래 있던 자리는 기본 색으로
            changeColor(newBlockArray, currentBlockIdx); //회전 후 자리는 새로운 색으로
            currentBlockArray = newBlockArray; //블록 위치 없데이트
        }
    }
    public void clearRow(){
        for (int i=0;i<20;i++){
            int clearFlag = 1;
            for (int j=0;j<10;j++){
                if (dp[i][j]==0) {
                    clearFlag = 0;
                    break;
                }
            }
            if (clearFlag==1) {
                //그 줄 위에 있는 것 다 내려야되고, 색깔도 다 바뀌야됨, 점수 올리기(+10)
                mark += 10;
                scoreLabel.setText(name + " : " + mark + " 점");
                for (int k=i;k>0;k--){
                    for (int j=0;j<10;j++){
                        dp[k][j] = dp[k-1][j]; //바로 위의 줄 기록 아래로 내림
                        switch(dp[k][j]){ //그림도 바꿔주기
                            case 0:
                                blockLabel[k][j].setIcon(Image.basicImage);
                                break;
                            case 1:
                                blockLabel[k][j].setIcon(Image.IMino);
                                break;
                            case 2:
                                blockLabel[k][j].setIcon(Image.OMino);
                                break;
                            case 3:
                                blockLabel[k][j].setIcon(Image.TMino);
                                break;
                            case 4:
                                blockLabel[k][j].setIcon(Image.JMino);
                                break;
                            case 5:
                                blockLabel[k][j].setIcon(Image.LMino);
                                break;
                            case 6:
                                blockLabel[k][j].setIcon(Image.SMino);
                                break;
                            case 7:
                                blockLabel[k][j].setIcon(Image.ZMino);
                                break;
                        }
                    }
                }
                for (int j=0;j<10;j++){ // 맨 위의 줄 0으로 초기화 및 그림 변환
                    dp[0][j] = 0;
                    blockLabel[0][j].setIcon(Image.basicImage);
                }
            }
        }
    }
    public void updateRank(){
        for (int i=0;i<5;i++){ //앞에 있을수록 점수 높아짐
            if (mark>=markList[i] || ((markList[i]==0 && nameList[i]==null))){ //이름과 점수 배열에 넣기
                for (int j=4;j>i;j--){
                    markList[j] =  markList[j-1];
                    nameList[j] = nameList[j-1];
                }
                markList[i] = mark;
                nameList[i] = (name.length()>6) ? name.substring(0, 7) + "..." : name;
                break;
            }
        }

        //GUI 점수창 보여주기
        if (nameList[0]!=null) rank1Label.setText("1. " + nameList[0] + " " + markList[0]);
        if (nameList[1]!=null) rank2Label.setText("2. " + nameList[1] + " " + markList[1]);
        if (nameList[2]!=null) rank3Label.setText("3. " + nameList[2] + " " + markList[2]);
        if (nameList[3]!=null) rank4Label.setText("4. " + nameList[3] + " " + markList[3]);
        if (nameList[4]!=null) rank5Label.setText("5. " + nameList[4] + " " + markList[4]);
    }
    public void gameOver(){
        //다시하기, 종료, 점수판 창 뜨게 만들기 만들기
        updateRank();
        timerStop = 1;
        String options[] = {"다시하기", "종료"};
        int answer = JOptionPane.showOptionDialog(null, "즐거운 시간!", "종료창", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "다시하기");
        if (answer==0) restart();
        else System.exit(0);
    }
    public void restart(){
        //기본 세팅 초기화 및 판 empty하게 만들기
        for (int i=0;i<20;i++){
            for (int j=0;j<10;j++){
                blockLabel[i][j].setIcon(Image.basicImage);
            }
        }
        dp = new int[20][10];
        rnd = new Random();
        currentBlockArray = new int[4][2];
        mark = 0;
        randomNumberCheck = new int[8];
        holdBlockIdx = 0;
        holdCnt = 0;
        turnCnt=0;

        //다시 게임시작
        name = JOptionPane.showInputDialog("이름을 입력하시오.");
        scoreLabel.setText(name +" : " + mark + " 점");

        //다음 블록 랜덤 생성 그리고 randomGenerator 실행
        int temp = rnd.nextInt(7) + 1;
        randomNumberCheck[temp] = 1;
        nextBlockIdx = temp;
        randomBlockGenerator();

        //타이머 다시 가동
        timerStop = 0;
    }
}  //테트리스 게임 클래스
public class Main {
    public static void main(String[] args) {
        tetrisGame tetris = new tetrisGame();
        tetris.startNewGame();
    }
} //실제 구동 메인 클라스