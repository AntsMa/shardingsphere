/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.core.parse.integrate.asserts.insert;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.shardingsphere.core.parse.integrate.asserts.SQLStatementAssertMessage;
import org.apache.shardingsphere.core.parse.integrate.jaxb.insert.ExpectedAssignment;
import org.apache.shardingsphere.core.parse.sql.context.expression.SQLExpression;
import org.apache.shardingsphere.core.parse.sql.context.expression.SQLIgnoreExpression;
import org.apache.shardingsphere.core.parse.sql.context.expression.SQLNumberExpression;
import org.apache.shardingsphere.core.parse.sql.context.expression.SQLParameterMarkerExpression;
import org.apache.shardingsphere.core.parse.sql.context.expression.SQLTextExpression;
import org.apache.shardingsphere.test.sql.SQLCaseType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class AssignmentAssert {
    
    private final SQLStatementAssertMessage assertMessage;
    
    private final SQLCaseType sqlCaseType;
    
    /**
     * Assert Assignment.
     *
     * @param actual actual assignment
     * @param expected expected assignment
     */
    public void assertAssignment(final SQLExpression actual, final ExpectedAssignment expected) {
        if (SQLCaseType.Placeholder == sqlCaseType) {
            assertThat(assertMessage.getFullAssertMessage("SQL expression type for placeholder error: "), actual.getClass().getSimpleName(), is(expected.getTypeForPlaceholder()));
            assertThat(assertMessage.getFullAssertMessage("SQL expression text for placeholder error: "), getText(actual), is(expected.getTextForPlaceholder()));
        } else {
            assertThat(assertMessage.getFullAssertMessage("SQL expression type for literal error: "), actual.getClass().getSimpleName(), is(expected.getTypeForLiteral()));
            assertThat(assertMessage.getFullAssertMessage("SQL expression text for literal error: "), getText(actual), is(expected.getTextForLiteral()));
        }
    }
    
    private String getText(final SQLExpression expression) {
        if (expression instanceof SQLParameterMarkerExpression) {
            return "" + ((SQLParameterMarkerExpression) expression).getIndex();
        }
        if (expression instanceof SQLTextExpression) {
            return ((SQLTextExpression) expression).getText();
        }
        if (expression instanceof SQLNumberExpression) {
            return "" + ((SQLNumberExpression) expression).getNumber();
        }
        if (expression instanceof SQLIgnoreExpression) {
            return ((SQLIgnoreExpression) expression).getExpression();
        }
        return "";
    }
}