/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ListIterator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.calibrations.K_Arm;
import frc.robot.commands.CT_ArmCntrl;
import frc.robot.models.PositionData;
import frc.robot.models.GamePieces;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  WPI_TalonSRX Lift = new WPI_TalonSRX(RobotMap.LiftMotorAddress);
  /************************************************
   * Section depreciated of ARMLEVELS, use K_Arm.ARM_POS_LEVEL to replace this
   * variable Reason for this is to provide a better model for how
   */
  private int setPoint = 0;
  public int ballPoint = 0;
  public int panelPoint = 0;
  public boolean AUTOMATIC_ACTIVE = false;
  private ListIterator<PositionData> currentIterator = K_Arm.ALL_POS_DATA.listIterator(1);
  private PositionData currentPositionData = K_Arm.ALL_POS_DATA.get(0);

  GamePieces gamePieces = GamePieces.Cargo;

  public Arm() {
    Lift.configMotionCruiseVelocity(11000);
    Lift.configMotionAcceleration(12000);
    Lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    Lift.config_kF(0, 0.11);
    Lift.config_kP(0, 0.13);
    Lift.config_kI(0, 0.0001);
    Lift.config_kD(0, 0.0);
    Lift.configForwardSoftLimitThreshold(K_Arm.ALL_POS_DATA.get(K_Arm.ALL_POS_DATA.size() - 1).location);
    Lift.configForwardSoftLimitEnable(true);
  }

  /**
   * Increments current List Position regardless of List being used
   * 
   * @return Returns the new position data
   */
  public PositionData incrementPosition() {
    if (currentIterator.hasNext()) {
      currentPositionData = currentIterator.next();
    }
    return currentPositionData;
  }

  /**
   * Decrements current List Position regardless of List being used
   * 
   * @return Returns the new position data
   */
  public PositionData decrementPosition() {
    if (currentIterator.hasPrevious()) {
      currentPositionData = currentIterator.previous();
    }
    return currentPositionData;
  }

  /**
   * Sets the Arm to floor cargo pickup
   * 
   * @return Returns the new position data
   */
  public PositionData resetToFloorCargoPickup() {
    if (gamePieces != GamePieces.Cargo) {
      gamePieces = GamePieces.Cargo;
    }
    currentIterator = K_Arm.BALL_POS_DATA.listIterator(0);
    currentPositionData = currentIterator.next();
    return currentPositionData;
  }

  public PositionData resetToHABPanelPickup() {
    if (gamePieces != GamePieces.HatchPanel) {
      gamePieces = GamePieces.HatchPanel;
    }
    currentIterator = K_Arm.PANEL_POS_DATA.listIterator(0);
    currentPositionData = currentIterator.next();
    return currentPositionData;
  }

  public GamePieces getGamePieceType() {
    return gamePieces;
  }

  public PositionData getCurrenPositionData() {
    return currentPositionData;
  }

  public void placeLocationTexttoSDB() {
    SmartDashboard.putString("Arm Level", getCurrenPositionData().name + " " + getCurrenPositionData().type);
  }

  public void placeArmDatatoSDB() {
    double[] input = { (double) liftRawPosition(), (double) liftRawVelocity(),
        (double) getCurrenPositionData().location, (double) armVoltage(), (double) armError() };
    SmartDashboard.putNumberArray("Arm Data", input);
  }

  /**
   * @param setPoint the setPoint to set
   */
  public void setSetPoint(int setPoint) {
    this.setPoint = setPoint;
  }

  public int liftRawPosition() {
    return Lift.getSelectedSensorPosition();
  }

  public int liftRawVelocity() {
    return Lift.getSelectedSensorVelocity();
  }

  public void LiftByVoltage(double Power) {
    Lift.set(ControlMode.PercentOutput, Power);
  }

  public void VelArm(int Vel) {
    Lift.set(ControlMode.Velocity, Vel);
  }

  public double armVoltage() {
    return Lift.getMotorOutputVoltage();
  }

  /*
   * Section for specifically controlling the arm
   */

  /************
   * Sets Motion Magic for the controller
   * 
   * @param Pos Enter the encoder Ticks you wish the arm to travel too
   */
  public void MMArm(int Pos) {
    Lift.set(ControlMode.MotionMagic, Pos);
  }

  public void armSafety(boolean safety) {
    Lift.setSafetyEnabled(safety);
  }

  public int armError() {
    return Lift.getClosedLoopError();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new CT_ArmCntrl());
  }
}
