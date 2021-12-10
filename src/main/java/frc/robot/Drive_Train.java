package frc.robot;
//these imports are for using spark motors and their setup
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//this is the import for setting up a joystick
import edu.wpi.first.wpilibj.Joystick;

import static java.lang.Math.abs;

//  this drives it
public class Drive_Train {
    //these are how you create spark mot4ors on the robot
    // drive motors
    public static CANSparkMax leftMotor = new CANSparkMax(3, MotorType.kBrushed);
    public static CANSparkMax leftFollower = new CANSparkMax(4, MotorType.kBrushed);
    public static CANSparkMax rightMotor = new CANSparkMax(1, MotorType.kBrushed);
    public static CANSparkMax rightFollower = new CANSparkMax(2, MotorType.kBrushed);
    // climber motor
    public static CANSparkMax climberMotor = new CANSparkMax(6, MotorType.kBrushed);
    // raise/lower intake
    public static CANSparkMax raiseIntakeMotor = new CANSparkMax(7, MotorType.kBrushed);
    // intake feed motor
    public static CANSparkMax intakeMotor = new CANSparkMax(5, MotorType.kBrushed);
    //this is how you create a joystick
    public static Joystick controller = new Joystick(0);
    /*this is how you create a function 
    using public means you can access it in other files like Robot.java
    using void means there is no return value so this can just be called as a function
    */
    public static void Driveinit(){
        //this is the Init function because it should only be called once per robot drive inverting the left side makes it so the motors will drive in the same direction
        leftMotor.setInverted(true);
        //setting the secondary motors to followers will make it so they copy the leader motors and act in pairs
        leftFollower.follow(leftMotor);
        rightFollower.follow(rightMotor);
    }
    
    public static void Drive() {
        int cont = 1;
        // cont = 1 for joystick
        // cont = 2 for dualshock
        if (cont == 1) {
            // drive section
            double leftfwd = controller.getRawAxis(2);
            double rightfwd = controller.getRawAxis(2);
            double lefttrn = controller.getRawAxis(1);
            double righttrn = controller.getRawAxis(1);
            System.out.println(leftfwd);
            double leftaxis = leftfwd/3 + lefttrn;
            double rightaxis = rightfwd/3 - righttrn;
            double lmotor = 0;
            double rmotor = 0;
            if (leftaxis > 1) {
                lmotor = leftaxis/abs(leftaxis);
                rmotor = rightaxis/abs(leftaxis);
            }
            else if (rightaxis > 1) {
                lmotor = leftaxis/abs(rightaxis);
                rmotor = rightaxis/abs(rightaxis);
            }
            else {
                lmotor = leftaxis;
                rmotor = rightaxis;
            }
            leftMotor.set(lmotor);
            rightMotor.set(rmotor);
            // System.out.println(leftMotor.getBusVoltage());
            // System.out.println(rightMotor.getBusVoltage());

            // climber section
            double climb = 0;
            if (controller.getRawButton(7) == true) {
                climb = 0.4;
            }
            else if (controller.getRawButton(8) == true) {
                climb = -0.4;
            }
            climberMotor.set(climb);

            // raise/lower intake section
            double raiseIntake = 0;
            if (controller.getRawButton(3) == true) {
                raiseIntake = -0.2;
            }
            else if (controller.getRawButton(5) == true) {
                raiseIntake = 0.2;
            }
            raiseIntakeMotor.set(raiseIntake);
            
            // intake feed section
            double intakepwr = 0;
            if (controller.getRawButton(4) == true) {
                intakepwr = -0.5;
            }
            else if (controller.getRawButton(6) == true) {
                intakepwr = 0.5;
            }
            intakeMotor.set(intakepwr);


        }
    }

    public static void DriveDis(){
        //once the robot is disabled this will shut the motors off
        leftMotor.set(0);
        rightMotor.set(0);
        raiseIntakeMotor.set(0);
        intakeMotor.set(0);
    }
}
