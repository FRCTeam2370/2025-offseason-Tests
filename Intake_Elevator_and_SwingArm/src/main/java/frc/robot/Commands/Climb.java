// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Subsystems.IntakeSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Climb extends Command {
  DoubleSupplier leftTrigger, rightTrigger;
  double pos;
  /** Creates a new Climb. */
  public Climb(DoubleSupplier leftTrigger, DoubleSupplier rightTrigger, IntakeSubsystem mIntakeSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.leftTrigger = leftTrigger;
    this.rightTrigger = rightTrigger;
    addRequirements(mIntakeSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pos = IntakeSubsystem.getIntakePos();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("left trigger: "+ leftTrigger);
    System.out.println("right trigger: " + rightTrigger);
    if(leftTrigger.getAsDouble() > 0.1 && IntakeSubsystem.getIntakePos() < Constants.MechanismConstants.minIntakeVal){
      pos += 1;
    }else if(rightTrigger.getAsDouble() > 0.1 && IntakeSubsystem.getIntakePos() > Constants.MechanismConstants.maxIntakeVal){
      pos -= 1;
    }

    IntakeSubsystem.setIntakePose(pos);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
