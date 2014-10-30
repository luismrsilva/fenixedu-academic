/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Core.
 *
 * FenixEdu Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Core.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.fenixedu.predicates;

import java.util.stream.Collectors;

import net.sourceforge.fenixedu.domain.Degree;
import net.sourceforge.fenixedu.domain.MarkSheet;
import net.sourceforge.fenixedu.domain.accessControl.academicAdministration.AcademicAccessRule;
import net.sourceforge.fenixedu.domain.accessControl.academicAdministration.AcademicOperationType;
import net.sourceforge.fenixedu.injectionCode.AccessControlPredicate;

import org.fenixedu.bennu.core.security.Authenticate;

public class MarkSheetPredicates {

    public static final AccessControlPredicate<MarkSheet> confirmPredicate = new AccessControlPredicate<MarkSheet>() {

        @Override
        public boolean evaluate(final MarkSheet markSheet) {
            return AcademicPredicates.MANAGE_MARKSHEETS.evaluate(null)
                    && (!markSheet.isRectification() || checkRectification(markSheet.getCurricularCourse().getDegree()));
        }

    };

    public static final AccessControlPredicate<MarkSheet> editPredicate = new AccessControlPredicate<MarkSheet>() {

        @Override
        public boolean evaluate(final MarkSheet markSheet) {
            return hasScientificCouncilRole()
                    || hasTeacherRole()
                    || (AcademicPredicates.MANAGE_MARKSHEETS.evaluate(null)
                            && (!markSheet.isRectification() || checkRectification(markSheet.getCurricularCourse().getDegree())) && (!markSheet
                            .isDissertation() || checkDissertation(markSheet.getCurricularCourse().getDegree())));
        }

    };

    public static final AccessControlPredicate<MarkSheet> rectifyPredicate = new AccessControlPredicate<MarkSheet>() {

        @Override
        public boolean evaluate(final MarkSheet markSheet) {
            return checkRectification(markSheet.getCurricularCourse().getDegree())
                    && (!markSheet.isDissertation() || checkDissertation(markSheet.getCurricularCourse().getDegree()));
        }
    };

    public static final AccessControlPredicate<MarkSheet> removeGradesPredicate = new AccessControlPredicate<MarkSheet>() {

        @Override
        public boolean evaluate(final MarkSheet markSheet) {
            return AcademicAccessRule.getDegreesAccessibleToFunction(AcademicOperationType.REMOVE_GRADES, Authenticate.getUser())
                    .collect(Collectors.toSet()).contains(markSheet.getCurricularCourse().getDegree());
        }
    };

    static public boolean checkRectification(Degree degree) {
        return AcademicAccessRule
                .getDegreesAccessibleToFunction(AcademicOperationType.RECTIFICATION_MARKSHEETS, Authenticate.getUser())
                .collect(Collectors.toSet()).contains(degree);
    }

    static public boolean checkDissertation(Degree degree) {
        return AcademicAccessRule
                .getDegreesAccessibleToFunction(AcademicOperationType.DISSERTATION_MARKSHEETS, Authenticate.getUser())
                .collect(Collectors.toSet()).contains(degree);
    }

    private static boolean hasScientificCouncilRole() {
        return RolePredicates.SCIENTIFIC_COUNCIL_PREDICATE.evaluate(null);
    }

    private static boolean hasTeacherRole() {
        return RolePredicates.TEACHER_PREDICATE.evaluate(null);
    }
}
