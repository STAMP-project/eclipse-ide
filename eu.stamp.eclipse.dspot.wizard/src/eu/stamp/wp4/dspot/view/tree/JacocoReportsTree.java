package eu.stamp.wp4.dspot.view.tree;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import eu.stamp.wp4.dspot.wizard.json.DSpotTestClassJSON;
import eu.stamp.wp4.dspot.wizard.json.DSpotTimeJSON;

public class JacocoReportsTree extends DSpotReportsTree {
	
	private int amplifiedTests;
	
	public JacocoReportsTree (DSpotTestClassJSON info,int amplifiedTests) {
		super(info,"Jacoco"); this.amplifiedTests = amplifiedTests;
	}

	public JacocoReportsTree(DSpotTestClassJSON info) { 
		super(info,"Jacoco"); this.amplifiedTests = 0;
		}

	@Override
	public void createTree(Tree tree,DSpotTimeJSON time) {
		
		TreeItem rootItem = new TreeItem(tree,SWT.NONE);
		rootItem.setText(0,"Test Class name : ");
		rootItem.setText(1,info.name);
		
		TreeItem item = new TreeItem(rootItem,SWT.NONE);
		item.setText(0, "Amplification time : ");
		item.setText(1, String.valueOf(time.getClassTime(info.name)) + " ms");
		
		item = new TreeItem(rootItem,SWT.NONE);
		item.setText(0,"Initial instruction coverage : "
				+ "\n initialInstructionCovered / initialInstructionTotal ");
		item.setText(1,
				String.format("%.2f",info.percentageinitialInstructionCovered) + " %");
		
		item = new TreeItem(rootItem,SWT.NONE);
		item.setText(0,"Amplified instruction coverage: "
				+ "\n amplifiedInstructionCovered / amplifiedInstructionTotal");
		item.setText(1, String.format("%.2f",
				info.percentageamplifiedInstructionCovered) + " %");
		
		if(amplifiedTests != 0 && info.nbOriginalTestCases != 0) {
		item = new TreeItem(rootItem,SWT.NONE);
		item.setText(0,"Amplified tests/original tests : ");
		item.setText(1,String.valueOf(String.valueOf(amplifiedTests) 
				+ "/" + String.valueOf(info.nbOriginalTestCases)));
		}
	}	
}
