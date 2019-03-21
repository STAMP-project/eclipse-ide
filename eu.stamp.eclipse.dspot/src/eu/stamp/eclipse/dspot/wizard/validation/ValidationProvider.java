package eu.stamp.eclipse.dspot.wizard.validation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.richclientgui.toolbox.validation.IFieldErrorMessageHandler;
import com.richclientgui.toolbox.validation.IQuickFixProvider;
import com.richclientgui.toolbox.validation.ValidatingField;
import com.richclientgui.toolbox.validation.string.StringValidationToolkit;
import com.richclientgui.toolbox.validation.validator.IFieldValidator;

public abstract class ValidationProvider {

public static final int VALIDATOR_DEFAULT = 1;	
	
public static Text getTextWithvalidation(Composite parent,Object page,String name,
		int typeOfValidator,boolean required) {
	
	IFieldErrorMessageHandler handler = ErrorHandlerFactory.getHandler(page);
	if(handler == null) return new Text(parent,SWT.BORDER);
	IFieldValidator<String> validator = getStringValidator(typeOfValidator,name);
	if(validator == null) return new Text(parent,SWT.BORDER);
	StringValidationToolkit kit = new StringValidationToolkit(SWT.TOP | SWT.LEFT,2,true);
	kit.setDefaultErrorMessageHandler(handler);
	ValidatingField<String> field = kit.createTextField(parent,validator,required,"");
	IQuickFixProvider<String> quickFixer = getQuickFixer(typeOfValidator);
	if(quickFixer != null) field.setQuickFixProvider(quickFixer);
	return (Text)field.getControl();
}

private static IFieldValidator<String> getStringValidator(int type,String name){
	switch(type) {
	case VALIDATOR_DEFAULT : return new IFieldValidator<String>() {
		@Override
		public String getErrorMessage() {
			return name 
					+ "contains non allowed characters (allowed : alphanumerical, _ , - )";
		}
		@Override
		public String getWarningMessage() {
			return null;
		}
		@Override
		public boolean isValid(String arg) {
			if(arg == null || arg.isEmpty()) return true;
			arg = arg.replaceAll("[0-9a-zA-Z]","").replaceAll("_","").replaceAll("-","");
			return arg.isEmpty();
		}
		@Override
		public boolean warningExist(String arg) {
	         return false;
		}
	};
	}
	return null;
}

private static IQuickFixProvider<String> getQuickFixer(int type){
	switch(type) {
	case VALIDATOR_DEFAULT : return new IQuickFixProvider<String>() {
		@Override
		public boolean doQuickFix(ValidatingField<String> field) {
			String content = field.getContents();
			content = content.replaceAll("[^0-9a-zA-Z_-]","");
			field.setContents(content);
			return true;
		}
		@Override
		public String getQuickFixMenuText() {
			return "remove non allowed characters";
		}
		@Override
		public boolean hasQuickFix(String arg) {
			if(arg == null || arg.isEmpty()) return false;
			arg = arg.replaceAll("[0-9a-zA-Z]","").replaceAll("_","").replaceAll("-","");
			return !arg.isEmpty();
		}
	};
	}
	return null;
}
}
