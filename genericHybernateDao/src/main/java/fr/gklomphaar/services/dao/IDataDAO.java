/**
 * 
 */
package fr.gklomphaar.services.dao;

import java.util.List;

import fr.gklomphaar.services.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.services.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.services.dao.generichybernate.WhereClauseBuilder.WhereClause;


public interface IDataDAO<DataType> {
	/**
	 * Add a new element to the DAO
	 * @param data the new element
	 * @throws DaoSaveObjectException thrown when there are problems with saving the new element 
	 */
	public void create(DataType data) throws DaoSaveObjectException;
	/**
	 * Retrieve all elements in the DAO
	 * @return A list containing all elements
	 * @throws DaoLoadObjectException  thrown when there are problems with reading the elements
	 */
	public List<DataType> readAll() throws DaoLoadObjectException;
	/**
	 * @param Data search for a specific field of data in the DAO
	 * @param Matcher defines which search method should be used
	 * @return A list of found elements
	 * @throws DaoLoadObjectException thrown when there are problems with reading the elements
	 */
	public List<DataType> search(DataType data, WhereClause whereClause) throws DaoLoadObjectException;
	/**
	 * Update a specific element in the DAO
	 * @param data Element to be updated
	 * @throws DaoSaveObjectException thrown when there are problems with saving the objects
	 */
	public void update(DataType data) throws DaoSaveObjectException;
	/**
	 * Delete a specific element in the DAO
	 * @param data element to be deleted
	 * @throws DaoSaveObjectException thrown when there are problems with deleting the objects
	 */
	public void delete(DataType data) throws DaoSaveObjectException;
}
