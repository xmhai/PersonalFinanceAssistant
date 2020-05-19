import React from 'react';
import { Link } from 'react-router-dom';
import { Container, Dropdown, Image, Menu } from 'semantic-ui-react'
  
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
                        <Menu.Item><Link to={`/`}>Home</Link></Menu.Item>
                        <Menu.Item><Link to={`/account`}>Account</Link></Menu.Item>

                        <Dropdown item text='Stock'>
                            <Dropdown.Menu>
                                <Dropdown.Item text='Portfolio' as={Link} to='/portfolio' />
                                <Dropdown.Item text='Transaction' as={Link} to='/transaction' />
                                <Dropdown.Item text='Dividend' as={Link} to='/dividend' />
                            </Dropdown.Menu>
                        </Dropdown>

                        <Dropdown item text='Config'>
                            <Dropdown.Menu>
                                <Dropdown.Item text='Stock' as={Link} to='/stock' />
                            </Dropdown.Menu>
                        </Dropdown>
                    </Container>
                </Menu>
            </div>
        );
    }
}

export default AppMenu;