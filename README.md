# FluentPopupMenu

A Material design context menu inspired by windows 11 for android. Very much like the popup menu that can be found in pixel launcher, this context menu pinpoints the view the menu is above or below with an arrow. The menu itself inflates a context menu that contains a minibar action menu and a detailed list action menu. The minibar will appear above the arrow or below the object in view. The developer also has options to tap into 2 button groups above and below the detailed list action menu. 

RULE OF THUMB
-The minibar should be used for universal actions. Add, Remove, Share, Expand, etc...Icons that everyone knows what they mean todo universally. The minibar has a click and long click action so you can add a title for long click but complete the action on click.
-Button Groups should only be used for immediate attention, expanding to another menu, shortcuts or for dismissal for older platforms. They should not be used for list of actions or not contain more than 3 actions.
-The detailed menu list is for detailed actions for users who probably wouldn't recongnize what an icon represents or if an item has individual actions for the object.
-Try not to use more than 4 action items in the detailed list. The Minibar is scrollable but users may not know if the list can scroll so limit yourself to the menu. We could find creative ways to indicate their are more than 4-5 items in the minibar but the listed actions will stretch the context menu. The button groups will also stretch the menu. This will cause issues with the arrow posisitioning itself against the view.


SETUP
--WIP--

Starting Up

You will need to create an array list using the FluentActionItems provided by the library. The first list will be for the minibar. The second list will be for your detailed action list. You are not required to use both list, it just depends where your action view will be located, if it moves or if its on the edge of the screen. In this demo, I will setup all the action list but allow you to understand it is optional.

ArrayList<FluentActionItem> simpleList = new ArrayList<>();
        simpleList.add(new FluentActionItem(1, "ADD", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_add_24), true));
        simpleList.add(new FluentActionItem(2, "SAVE", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_save_alt_24), false));
        simpleList.add(new FluentActionItem(3, "SHARE", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_share_24), false));
  
The FluentActionItem is provided by the library and allows the menu communicate with the action as intended. For the minibar, the title is completely optional as it is not seen but is accessible just as easily. You could use it as an accessibility option. The FluentActionItem request a string title, an icon drawable, and to decide if the action is sticky or not (To dismiss the menu on click or not)

Setup the menu in your class!
  
  FluentPopupMenu fluentPopupMenu = new FluentPopupMenu(mContext);

  fluentPopupMenu.setBackgroundRadias(22);
  fluentPopupMenu.setAllowFadeAnimation(true);
  fluentPopupMenu.setFadeDuration(400);
  fluentPopupMenu.setListAnimationDuration(500, 100);
  
Here, the developer has action to a few options for design and appearance. You have control over the corner radias, toggle the intro and exit animation, the animations durations and control the intro animations for the context menu list. This allows you to somewhat style the menu to your apps own style or appearance. We will be working on more options to control as the menu is used in different instances to learn more about its usage.
  
  add your arraylist to the simple list or the minibar!
  
  fluentPopupMenu.setupSimpleList(simpleList);
  
  initialize the minibar and add the MenuItemTouchListener (Provided by the library) so we can control click actions on the items we've added.
  
  fluentPopupMenu.getSimpleList().addOnItemTouchListener(new MenuItemTouchListener(mContext, fluentPopupMenu.getSimpleList(), new MenuItemTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(view.getContext(), "Position clicked : " + simpleList.get(position).getActionId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
              //can be used as a way for the user to discover the lable of the minibar action
                Toast.makeText(view.getContext(), simpleList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwiped(View view, int position) {
              //normally we wouldn't use this in this instance but we never know...
            }
        }));
  
Here we are going to setup the button groups. This is completely optional but sometimes we need an area for some specific action that pops out for the user. Sometimes its exclusive to the object in view or a shortcut or an expand option. One thing to take into consideration that Android 6 and below sometimes requres an action to dismiss a popup window, this button group is a perfect place to add a dismiss option for users. Specifically the bottom group.
  
  Create an array list using the FluentButtonAction Items
  
  ArrayList<FluentButtonActionItem> topButtonList = new ArrayList<>();
  
  Next, take that list and add an action to the list like below. The Action Item will require an ID, a String Label, sticky perk and a click listener. We demo this list below with 2 buttons for the top group.
  
        topButtonList.add(new FluentButtonActionItem(1, "Drive D", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Action One Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
        topButtonList.add(new FluentButtonActionItem(2, "Drive E", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Action Two Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
  
  make sure to tell the fluent menu to show the button group!
  
        fluentPopupMenu.setTopActionButtonListVisibility(View.VISIBLE);
  
  now, add the list to the group
  
        fluentPopupMenu.setupTopButtonGroup(topButtonList);

  
  Here we are making a bottom button group with one button.
  
        ArrayList<FluentButtonActionItem> bottomButtonList = new ArrayList<>();
        bottomButtonList.add(new FluentButtonActionItem(1, "Action", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Action Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
  
        fluentPopupMenu.setBottomActionButtonListVisibility(View.VISIBLE);
  
        fluentPopupMenu.setupBottomButtonGroup(bottomButtonList);
  
  Finally, we are creating the detailed action list for the Fluent Menu. It is the exact same as the minibar but besure to call the correct list when setting it up. In this case, its called the detailed list. Sometimes we forget to set the correct fluent menu list in the item click listener. Have a label is important for this list. Icons are optional but make the list look much more interactive.
  
  ArrayList<FluentActionItem> detailedList = new ArrayList<>();
        detailedList.add(new FluentActionItem(1, "Add", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_add_24), true));
        detailedList.add(new FluentActionItem(3, "Save", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_save_alt_24), false));
        detailedList.add(new FluentActionItem(4, "Search", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_search_24), false));
        detailedList.add(new FluentActionItem(2, "Share", ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_share_24), false));

        fluentPopupMenu.setupDetailedList(detailedList);
  
        fluentPopupMenu.getDetailedList().addOnItemTouchListener(new MenuItemTouchListener(mContext, fluentPopupMenu.getDetailedList(), new MenuItemTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(view.getContext(), "Position clicked : " + detailedList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(view.getContext(), "Position long clicked : " + detailedList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSwiped(View view, int position) {

            }
        }));
  
  If you need it, Fluent Menu comes with a dismiss listener!
  
  fluentPopupMenu.setOnDismissListener(new FluentPopupMenu.OnDismissListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(mContext, "Isn't this a cool menu? Dismissed...", Toast.LENGTH_SHORT).show();
            }
        });
  
  And finally...set the menu color and call the menu on an object.
  
In the case of the demo, im just randomly selecting a color for the menu. We recommend useing a light gray or mid black color as defaults for the light and dark system theme, using Material You for wallpaper colors or using a primary color based off of the object if it has a drawable or bitmap background. Color Provider class overs some coloring and tinting options that may be helpful.
  
  Random rnd = new Random();
  color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                
  fluentPopupMenu.setMenuColor(color);
  fluentPopupMenu.show(view);
