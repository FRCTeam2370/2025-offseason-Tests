// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ManipulatorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class IntakeAlgaeAuto extends Command {
  double speed;
  Timer timer = new Timer();
  /** Creates a new IntakeAlgae. */
  public IntakeAlgaeAuto(double speed, ManipulatorSubsystem mManipulatorSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.speed = speed;
    addRequirements(mManipulatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ManipulatorSubsystem.runManipulator(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(ManipulatorSubsystem.getManipulatorStatorCurrent() > 25){
      ManipulatorSubsystem.hasAlgae = true;
      return true;
    }else if(timer.get() > 2){
      ManipulatorSubsystem.runManipulator(0);
      return true;
    }else{
      return false;
    }
  }
}
