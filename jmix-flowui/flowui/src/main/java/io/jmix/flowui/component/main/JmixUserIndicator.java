/*
 * Copyright 2022 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jmix.flowui.component.main;

import com.google.common.base.Strings;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.EntityValues;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.core.usersubstitution.UserSubstitutionManager;
import io.jmix.flowui.kit.component.main.UserIndicator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.UserDetails;

public class JmixUserIndicator extends UserIndicator<UserDetails> implements ApplicationContextAware, InitializingBean {

    protected ApplicationContext applicationContext;
    protected CurrentUserSubstitution currentUserSubstitution;
    protected UserSubstitutionManager substitutionManager;
    protected MetadataTools metadataTools;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        autowireDependencies();
    }

    protected void autowireDependencies() {
        currentUserSubstitution = applicationContext.getBean(CurrentUserSubstitution.class);
        substitutionManager = applicationContext.getBeanProvider(UserSubstitutionManager.class)
                .getIfAvailable();
        metadataTools = applicationContext.getBean(MetadataTools.class);
    }

    @Override
    public void refreshUser() {
        getContent().removeAll();

        UserDetails user = currentUserSubstitution.getAuthenticatedUser();

        // todo user substitution
        /*List<UserDetails> currentAndSubstitutedUsers = new LinkedList<>();
        currentAndSubstitutedUsers.add(user);


        List<UserDetails> additionalUsers = substitutionManager != null
                ? substitutionManager.getCurrentSubstitutedUsers() : Collections.emptyList();

        currentAndSubstitutedUsers.addAll(additionalUsers);

        if (additionalUsers.size() > 0) {
            userComponent = createUserSelectionField(currentAndSubstitutedUsers);
        } else {
            userComponent = createUserIndicator(currentUserSubstitution.getAuthenticatedUser());
        }*/

        userComponent = createUserIndicator();

        updateUserIndicatorLabel(currentUserSubstitution.getAuthenticatedUser());

        getContent().add(userComponent);
        getContent().setTitle(generateUserTitle(user));

//        adjustWidth();
//        adjustHeight();
    }

        // todo user substitution
/*    protected HasValue<UserDetails> createUserSelectionField(List<UserDetails> currentAndSubstitutedUsers) {
        ComboBox<UserDetails> userCombobox = uiComponents.create(ComboBox.of(UserDetails.class));
        userCombobox.setOptionsList(currentAndSubstitutedUsers);
        userCombobox.setStyleName("jmix-user-select-combobox");
        userCombobox.setOptionCaptionProvider(this::generateUserCaption);
        userCombobox.setNullOptionVisible(false);
        userCombobox.setValue(currentUserSubstitution.getEffectiveUser());

        userCombobox.addValueChangeListener(this::substituteUser);

        return userCombobox;
    }*/

    /*protected void substituteUser(HasValue.ValueChangeEvent<UserDetails> valueChangedEvent) {
        UserDetails newUser = valueChangedEvent.getValue();
        UserDetails prevUser = valueChangedEvent.getPrevValue();

        if (newUser == null || prevUser == null) {//should not happen
            return;
        }

        if (currentUserSubstitution.getEffectiveUser().equals(newUser)) {
            return;
        }

        SubstituteUserAction substituteUserAction = new SubstituteUserAction(newUser,
                prevUser,
                messages,
                icons,
                substitutionManager)
                .withCancelAction(this::revertSelection);
        substituteUserAction.setPrimary(true);

        dialogs.createOptionDialog()
                .withCaption(messages.getMessage("substitutionConfirmation.caption"))
                .withMessage(messages.formatMessage("", "substitutionConfirmation.message",
                        generateUserCaption(newUser)))
                .withActions(
                        substituteUserAction,
                        new DialogAction(DialogAction.Type.CANCEL)
                                .withHandler(event -> revertSelection(prevUser))
                )
                .show();
    }*/

    /*protected void revertSelection(UserDetails oldUser) {
        userComponent.setValue(oldUser);
    }*/

    protected String generateUserTitle(UserDetails user) {
        String userTitle = super.generateUserTitle(user);

        if (!Strings.isNullOrEmpty(userTitle)) {
            return userTitle;
        } else if (EntityValues.isEntity(user)) {
            return metadataTools.getInstanceName(user);
        } else {
            return user.getUsername();
        }
    }
}
