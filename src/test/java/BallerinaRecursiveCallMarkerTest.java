/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import com.intellij.codeInsight.daemon.GutterMark;
import com.intellij.icons.AllIcons;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

import java.util.List;

public class BallerinaRecursiveCallMarkerTest extends LightPlatformCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "src/test/resources/testData/recursiveMarker";
    }

    public void testFunction1() {
        doTest(false);
    }

    public void testFunction2() {
        doTest(false);
    }

    public void testFunction3() {
        doTest(true);
    }

    public void testFunction4() {
        doTest(false);
    }

    public void testFunction5() {
        doTest(true);
    }

    private void doTest(boolean shouldBeEmpty) {
        myFixture.configureByFile(getTestName(false) + ".bal");
        List<GutterMark> guttersAtCaret = myFixture.findGuttersAtCaret();
        if (shouldBeEmpty) {
            if (!guttersAtCaret.isEmpty()) {
                fail("Unexpected gutter found.");
            }
        } else {
            if (guttersAtCaret.size() > 1) {
                fail("Multiple gutters found.");
            }
            if (guttersAtCaret.isEmpty()) {
                fail("No gutters found.");
            }
            GutterMark gutter = myFixture.findGuttersAtCaret().get(0);
            String text = gutter.getTooltipText();
            if (!"Recursive call".equals(text) || !AllIcons.Gutter.RecursiveMethod.equals(gutter.getIcon())) {
                fail("Incorrect Gutter found: " + text);
            }
        }
    }
}
