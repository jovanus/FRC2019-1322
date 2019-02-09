/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.pmArm;

/**
 * Add your docs here.
 */
public class PtypeLift extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  WPI_TalonSRX Lift = new WPI_TalonSRX(RobotMap.LiftMotorAddress);
  WPI_TalonSRX BallIntake = new WPI_TalonSRX(RobotMap.BallIntakeAddress);

  public PtypeLift(){
    Lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
  }

  public int liftRawPosition(){
    return Lift.getSelectedSensorPosition();
  }

  public int liftRawVelocity(){
    return Lift.getSelectedSensorVelocity();
  }

  public void intakePower(double Power){
    BallIntake.set(ControlMode.PercentOutput, Power);
  }

  public void LiftByVoltage(double Power){
    Lift.set(ControlMode.PercentOutput, Power);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new pmArm());
  }
}