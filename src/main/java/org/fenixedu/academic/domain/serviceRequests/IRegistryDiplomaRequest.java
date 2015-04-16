/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Academic.
 *
 * FenixEdu Academic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Academic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.academic.domain.serviceRequests;

import java.util.Locale;

import org.fenixedu.academic.domain.ExecutionYear;
import org.joda.time.LocalDate;

public interface IRegistryDiplomaRequest extends IProgramConclusionRequest {
    public LocalDate getConclusionDate();

    public ExecutionYear getConclusionYear();

    public String getFinalAverage(final Locale locale);

    public String getQualifiedAverageGrade(final Locale locale);

    public IDiplomaSupplementRequest getDiplomaSupplement();

    public String getDegreeName(final ExecutionYear year);
}
