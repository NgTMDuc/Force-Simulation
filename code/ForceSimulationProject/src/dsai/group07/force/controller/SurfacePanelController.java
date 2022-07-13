package dsai.group07.force.controller;

import dsai.group07.force.model.Simulation;
import dsai.group07.force.model.surface.Surface;
import dsai.group07.force.model.vector.FrictionForce;

import java.text.NumberFormat;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class SurfacePanelController {

	private Simulation simul;

	@FXML
	private TextField staticCoefTextField;

	@FXML
	private TextField kineticCoefTextField;

	@FXML
	private Slider staticCoefSlider;

	@FXML
	private Slider kineticCoefSlider;

	@FXML
	public void initialize() {
		staticCoefTextField.textProperty().bindBidirectional(staticCoefSlider.valueProperty(),
				NumberFormat.getNumberInstance());
		staticCoefTextField.textProperty().addListener(event -> {
			staticCoefTextField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
					!staticCoefTextField.getText().isEmpty()
							&& !staticCoefTextField.getText().matches("^[+-]?[0-9]+(?:\\\\.[0-9]+)?$"));
		});

		kineticCoefTextField.textProperty().bindBidirectional(kineticCoefSlider.valueProperty(),
				NumberFormat.getNumberInstance());
		kineticCoefTextField.textProperty().addListener(event -> {
			kineticCoefTextField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
					!kineticCoefTextField.getText().isEmpty()
							&& !kineticCoefTextField.getText().matches("^[+-]?[0-9]+(?:\\\\.[0-9]+)?$"));
		});
	}

	public void init(Simulation simul) {
		setSimul(simul);
	}

	public void setSimul(Simulation simul) {
		this.simul = simul;

		staticCoefTextField.textProperty().bindBidirectional(simul.getSur().staCoefProperty(),
				NumberFormat.getNumberInstance());

		kineticCoefTextField.textProperty().bindBidirectional(simul.getSur().kiCoefProperty(),
				NumberFormat.getNumberInstance());

		staticCoefSlider.valueProperty().bindBidirectional(simul.getSur().staCoefProperty());
		kineticCoefSlider.valueProperty().bindBidirectional(simul.getSur().kiCoefProperty());

		staticCoefTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println(staticCoefTextField.getText());
			System.out.println(staticCoefTextField.getText().matches("[0-1](?:\\\\.[0-9]+)?$"));
			System.out.println("0.5".matches("[0-1](?:\\\\.[0-9]+)?$"));
			try {
				simul.getSur().setStaCoef(Double.parseDouble(newValue));
			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				this.simul.getSur();
				alert.setContentText(e.getMessage() + "\nPlease input a number >= 0 and <= " + Surface.MAX_STA_COEF);
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
			}
		});

		kineticCoefTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				simul.getSur().setKiCoef(Double.parseDouble(newValue));
			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				this.simul.getSur();
				alert.setContentText(e.getMessage() + "\nPlease input a number >= 0 and <= " + Surface.MAX_STA_COEF);
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
			}
		});

		surfaceListener();

	}

	public void surfaceListener() {
		try {
			this.simul.getSur().staCoefProperty().addListener((observable, oldValue, newValue) -> {
				try {
//					System.out.println("Static Coeff Changed.");
//					System.out.println(this.simul.getaForce().getValue());
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			this.simul.getSur().kiCoefProperty().addListener((observable, oldValue, newValue) -> {
				try {
//					System.out.println("Kinetic Coeff Changed.");
					((FrictionForce) this.simul.getfForce()).updateFrictionForce();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
