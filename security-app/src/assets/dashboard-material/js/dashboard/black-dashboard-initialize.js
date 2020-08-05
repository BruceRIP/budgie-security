$(function() {
     initialize();
});
function initialize(){
    $sidebar = $('.sidebar');
    $navbar = $('.navbar');
    $main_panel = $('.main-panel');
    $full_page = $('.full-page');
    $sidebar_responsive = $('body > .navbar-collapse');
    sidebar_mini_active = true;
    white_color = true;
    window_width = $(window).width();
    fixed_plugin_open = $('.sidebar .sidebar-wrapper .nav li.active a p').html();

    if (white_color == false) {
      $('body').addClass('change-background');
      setTimeout(function() {
          $('body').removeClass('change-background');
          $('body').removeClass('white-content');
          $('#sidebar-menu').removeClass('sidebar-white');
      }, 900);
      white_color = false;
  } else {
       /*$('body').addClass('change-background');
        $('body').removeClass('change-background');
        $('body').addClass('white-content');
        $('#sidebar-menu').addClass('sidebar-white');*/
        $('body').addClass('white-content');
        $('#sidebar-menu').addClass('sidebar-white');
        $('#changeTheme .dropdown-menu').css('background', 'linear-gradient(#222a42, #1d253b)');
        $('#changeTheme li.header-title').css('color','#ffffff');
        $('#changeTheme li.adjustments-line .color-label').css('color','#ffffff');
        $('#main-panel').css('border-top', '2px solid #2A93BB');
      white_color = true;
  }

    $('.fixed-plugin a').click(function(event) {
        if ($(this).hasClass('switch-trigger')) {
            if (event.stopPropagation) {
                event.stopPropagation();
            } else if (window.event) {
                window.event.cancelBubble = true;
            }
        }
    });

$('.fixed-plugin .background-color span').click(function() {
    $(this).siblings().removeClass('active');
    $(this).addClass('active');
    var new_color = $(this).data('color');
    if ($sidebar.length != 0) {
        $sidebar.attr('data', new_color);
    }
    if ($main_panel.length != 0) {
        $main_panel.attr('data', new_color);
    }
    if ($full_page.length != 0) {
        $full_page.attr('filter-color', new_color);
    }
    if ($sidebar_responsive.length != 0) {
        $sidebar_responsive.attr('data', new_color);
    }
});

$('.switch-sidebar-mini input').on("switchChange.bootstrapSwitch", function() {
    var $btn = $(this);
    if (sidebar_mini_active == true) {
        $('body').removeClass('sidebar-mini');
        sidebar_mini_active = false;
        blackDashboard.showSidebarMessage('Sidebar mini deactivated...');
    } else {
        $('body').addClass('sidebar-mini');
        sidebar_mini_active = true;
        blackDashboard.showSidebarMessage('Sidebar mini activated...');
    }
    // we simulate the window Resize so the charts will get updated in realtime.
    var simulateWindowResize = setInterval(function() {
        window.dispatchEvent(new Event('resize'));
    }, 180);
    // we stop the simulation of Window Resize after the animations are completed
    setTimeout(function() {
        clearInterval(simulateWindowResize);
    }, 1000);
    });
    $('.switch-change-color input').on("switchChange.bootstrapSwitch", function() {
    var $btn = $(this);
    if (white_color == true) {
        $('body').addClass('change-background');
        setTimeout(function() {
            $('body').removeClass('change-background');
            $('body').removeClass('white-content');
            $('#sidebar-menu').removeClass('sidebar-white');
        }, 900);
        white_color = false;
    } else {
        $('body').addClass('change-background');
        setTimeout(function() {
            $('body').removeClass('change-background');
            $('body').addClass('white-content');
            $('#sidebar-menu').addClass('sidebar-white');

        }, 900);
        white_color = true;
    }
});
$('.light-badge').click(function() {
    $('body').addClass('white-content');
    $('#sidebar-menu').addClass('sidebar-white');
    $('#changeTheme .dropdown-menu').css('background', 'linear-gradient(#222a42, #1d253b)');
    $('#changeTheme li.header-title').css('color','#ffffff');
    $('#changeTheme li.adjustments-line .color-label').css('color','#ffffff');
    $('#main-panel').css('border-top', '2px solid #2A93BB');
});
$('.dark-badge').click(function() {
    $('body').removeClass('white-content');
    $('#sidebar-menu').removeClass('sidebar-white');
    $('#changeTheme .dropdown-menu').css('background', 'linear-gradient(#eff0f1, #f5f6fa)');
    $('#changeTheme li.header-title').css('color','#525f7f');
    $('#changeTheme li.adjustments-line .color-label').css('color','#525f7f');
    $('#main-panel').css('border-top', '2px solid #064848');
});
}
