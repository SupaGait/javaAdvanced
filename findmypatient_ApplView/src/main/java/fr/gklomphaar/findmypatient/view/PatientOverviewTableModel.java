/**
 * 
 */
package fr.gklomphaar.findmypatient.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import fr.gklomphaar.findmypatient.datamodel.Patient;

/**
 * @author Gerard
 *
 */
@SuppressWarnings("serial")
public class PatientOverviewTableModel extends AbstractTableModel {

	private List<Patient> patients;
	
	PatientOverviewTableModel()
	{
		this.patients = new ArrayList<Patient>();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return this.patients.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 9;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		
		if(rowIndex < this.patients.size())
		{
			switch(columnIndex)
			{
				case 0: value = this.patients.get(rowIndex).getpId(); break;
				case 1: value = this.patients.get(rowIndex).getSsnNo(); break;
				case 2: value = this.patients.get(rowIndex).getfName(); break;
				case 3: value = this.patients.get(rowIndex).getlName(); break;
				case 4: value = this.patients.get(rowIndex).getDob();  break;
				case 5: value = this.patients.get(rowIndex).getDisplayName(); break;
				case 6: value = this.patients.get(rowIndex).getCellNo(); break;
				case 7: value = this.patients.get(rowIndex).getEmail();break;
				case 8: value = this.patients.get(rowIndex).getroomNo(); break;
				
			}			
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
        String name = "??";
        switch (column) {
	        case 0:
	            name = "ID";
	            break;
            case 1:
                name = "SSN";
                break;
            case 2:
                name = "Firstname";
                break;
            case 3:
                name = "Lastname";
                break;
            case 4:
                name = "Date of birth";
                break;
            case 5:
                name = "Fullname";
                break;
            case 6:
                name = "Cell No";
                break;
            case 7:
                name = "Email";
                break;
            case 8:
                name = "Roomnumber";
                break;
        }
        return name;
	}

	/**
	 * @param allPatients list of patients which will be set as table data
	 */
	public void setPatients(List<Patient> allPatients) {
		// Add data and update table
		this.patients = allPatients;
		this.fireTableDataChanged();
	}
	
	public Patient getPatient(int rowIndex) {
		return this.patients.get(rowIndex);
	}

}
