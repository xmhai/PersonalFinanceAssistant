import React from 'react';
import {
    Container,
    Dropdown,
    Image,
    Menu,
  } from 'semantic-ui-react'
  
class AppMenu extends React.Component {
    render() {
        return (
            <div id="menu">
                <Menu fixed='top' inverted>
                    <Container>
                        <Menu.Item as='a' header>
                        <Image size='mini' src='/logo.png' style={{ marginRight: '1.5em' }} />
                        <font color="#F2B233">PFA</font>
                        </Menu.Item>
                        <Menu.Item as='a'>Home</Menu.Item>
                        <Menu.Item as='a'>Account</Menu.Item>

                        <Dropdown item simple text='Stock'>
                        <Dropdown.Menu>
                            <Dropdown.Item>Portofolio</Dropdown.Item>
                            <Dropdown.Item>Divident</Dropdown.Item>
                            <Dropdown.Item>Transaction</Dropdown.Item>
                        </Dropdown.Menu>
                        </Dropdown>

                        <Dropdown item simple text='Config'>
                        <Dropdown.Menu>
                            <Dropdown.Item>Stock List</Dropdown.Item>
                        </Dropdown.Menu>
                        </Dropdown>
                    </Container>
                </Menu>
            </div>
        );
    }
}

export default AppMenu;