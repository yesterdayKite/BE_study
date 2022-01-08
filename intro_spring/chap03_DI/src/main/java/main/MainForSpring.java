/*
MainForAssembler 클래스와 다른 점 :  Assembler클래스 대신 spring컨테이너인 ApplicationContext 사용
 */

package main;

import main.config.AppCtx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainForSpring {

    private static ApplicationContext ctx = null;

    public static void main(String[] args) throws IOException {

        // 스프링 컨테이너 생성. 스프링 컨테이너는, assmembler와 동일하게, 객체를 생성하고 의존객체를 주입한다.
        // assembler는 직접 객체를 생성하는 반면에
        // AnnotationConfigApplicationContext는 설정파일(AppCtx클래스)로부터 생성할 객체와 의존주입 대상을 정한다.

        ctx = new AnnotationConfigApplicationContext(AppCtx.class);

        // stdin으로 입력받기
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("명령어를 입력하세요.");
            String command = reader.readLine();

            if (command.equalsIgnoreCase("exit")) { // 프로그램 종료
                System.out.println("종료합니다.");
                break;
            }

            if (command.startsWith("new ")) {
                processNewCommand(command.split(" "));
                continue;
            } else if (command.startsWith("change ")) {
                processChangeCommand(command.split(" "));
                continue;
            } else if (command.startsWith("info ")){
                processInfoCommand(command.split(" "));
                continue;
            }
            else if (command.equals("list")){
                processListCommand();
                continue;
            }
            printHelp(); // 명령어 잘못 입력시 도움말 출

        }
    }

    private static void processNewCommand(String[] arg) {
        if (arg.length != 5){
            printHelp();
            return;
        }

        // 스프링 컨테이너로부터 이름이 "memberRegSvc"인 bean객체를 구한다.
        MemberRegisterService regSvc =
                ctx.getBean("memberRegSvc", MemberRegisterService.class);

        RegisterRequest req = new RegisterRequest();

        req.setEmail(arg[1]);
        req.setName(arg[2]);
        req.setPassword(arg[3]);
        req.setConfirmPassword(arg[4]);

        if (!req.isPasswordEqualToConfirmPassword()){
            System.out.println("암호와 확인이 일치하지 않습니다.\n");
            return;
        }
        try{
            regSvc.regist(req);
            System.out.println("등록했습니다.\n");
        } catch(DuplicateMemberException e){
            System.out.println("이미 존재하는 이메일입니다.\n");
        }
    }

    private static void processChangeCommand(String[] arg){
        if (arg.length != 4){
            printHelp();
            return;
        }

        // 스프링 컨테이너로부터 이름이 "changePwdSvc"인 bean객체를 구한다.
        ChangePasswordService changePwdSvc =
                ctx.getBean("changePwdSvc", ChangePasswordService.class);
        try{
            changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
            System.out.println("암호를 변경했습니다.\n");
        } catch(MemberNotFoundException e){
            System.out.println("존재하지 않는 이메일입니다.\n");
        } catch(WrongIdPasswordException e){
            System.out.println("이메일과 암호가 일치하지 않습니다..\n");
        }
    }

    private static void processListCommand(){
        MemberListPrinter listPrinter =
                ctx.getBean("listPrinter", MemberListPrinter.class);
        listPrinter.printAll();
    }

    private static void processInfoCommand(String[] arg){
        if (arg.length != 2){
            printHelp();
            return;
        }
        MemberInfoPrinter infoPrinter =
                ctx.getBean("infoPrinter", MemberInfoPrinter.class);
        infoPrinter.printMemberInfo(arg[1]);
    }

    private static void printHelp () {
        System.out.println();
        System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
        System.out.println("명령어 사용법 :");
        System.out.println("new 이메일 이름 암호 암호확인");
        System.out.println("change 이메일 현재비번 변경비번");
        System.out.println();
    }
}
