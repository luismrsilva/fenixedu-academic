/*
 * Created on 26/Mar/2003
 * 
 *  
 */
package Dominio;

import java.util.List;

/**
 * @author Jo�o Mota
 * @author jpvl
 */
public interface IProfessorship extends IDomainObject
{
    public ITeacher getTeacher();
    public IDisciplinaExecucao getExecutionCourse();
    public Double getCredits();
    public void setCredits(Double credits);

    public void setTeacher(ITeacher teacher);
    public void setExecutionCourse(IDisciplinaExecucao executionCourse);

    public List getAssociatedTeacherShiftPercentage();
    public void setAssociatedTeacherShiftPercentage(List associatedTeacherShiftPercentage);

}
